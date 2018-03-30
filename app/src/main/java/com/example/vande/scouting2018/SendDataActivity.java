package com.example.vande.scouting2018;

import android.Manifest;
import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Matt on 10/9/2017.
 */

public class SendDataActivity extends AppCompatActivity {

    @BindView(R.id.matchOrPit_RadiobtnGrp)
    public RadioGroup matchOrPitRadiobtnGrp;

    @BindView(R.id.concatFolder_editText)
    public EditText concatFolderEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        ButterKnife.bind(this);
    }

    /* This method will display the options menu when the icon is pressed
     * and this will inflate the menu options for the user to choose
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*This method will launch the correct activity
     *based on the menu option user presses
      */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.send_data:
                startActivity(new Intent(this, SendDataActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void concatenateData(View view) {
        if(PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            RadioButton radioButton = findViewById(matchOrPitRadiobtnGrp.getCheckedRadioButtonId());

            if(radioButton != null) {
                String dir = Environment.getExternalStorageDirectory() + "/" + concatFolderEditText.getText().toString();

                File folder = new File(dir);

                if(folder.exists() && concatFolderEditText.getText().toString().length() > 0) {
                    File[] files = folder.listFiles();

                    StringBuilder builder = new StringBuilder();
                    FileReader fileReader;
                    BufferedReader bufferedReader;
                    FileOutputStream fileOutputStream;

                    String type = radioButton.getText().toString().contains("Match") ? "Match" : "Pit";

                    if (files != null) {
                        try {
                            int fileCount = 0;
                            for (File file : files) {
                                if (file.getName().contains(type)) {
                                    fileCount++;
                                    fileReader = new FileReader(file);
                                    bufferedReader = new BufferedReader(fileReader);

                                    String line;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        builder.append(line + '\n');
                                    }
                                }
                            }
                            fileOutputStream = new FileOutputStream(new File(dir, "new.csv"), false);
                            fileOutputStream.write("teamNumber,matchNumber,startingLocation,baseline,autoCubesInSwitch,autoCubesInScale,numberOfCubesInExchange,numberOfCubesInTheirSwitch,numberOfCubesInOpSwitch,NumberOfCubesInScale,cubePickup,climb,canHelpOthersClimb,onPlatform,defense,fouls,scouterInitials".getBytes());
                            fileOutputStream.write(builder.toString().getBytes());
                            fileOutputStream.close();

                            Toast.makeText(this, "Successfully concatenated " + fileCount + " " + type + " files to new.csv!", Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            Log.d("Scouting", e.getMessage());
                            Toast.makeText(this, "Failed to concatenate data.", Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                    Toast.makeText(this, "Invalid folder name!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Select an option!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendMatchData(View view) {
        if(PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String file = "storage/emulated/0/Scouting/Match" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv";
            intent.setType("text/plain");
            intent.setPackage("com.android.bluetooth");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(file)));
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }

    public void sendPitData(View view) {
        if(PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String file = "storage/emulated/0/Scouting/Pit" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv";
            intent.setType("text/plain");
            intent.setPackage("com.android.bluetooth");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(file)));
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }

    public void sendRobotPhotos(View view) {
        if(PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/jpeg");
            intent.setPackage("com.android.bluetooth");
            String dir = "storage/emulated/0/Scouting/Photos/";

            File folder = new File(dir);
            File[] photos = folder.listFiles();

            ArrayList<Uri> toSend = new ArrayList<>();

            for (int i = 0; i < photos.length; i++) {
                toSend.add(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photos[i]));
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, toSend);
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }

    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}