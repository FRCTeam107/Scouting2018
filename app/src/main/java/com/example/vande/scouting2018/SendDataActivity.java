package com.example.vande.scouting2018;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import utils.BluetoothUtils;
import utils.PermissionUtils;

/**
 * Created by Matt on 10/9/2017.
 */

public class SendDataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
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
            String dir = "storage/emulated/0/Scouting/";

            File folder = new File(dir);
            File[] files = folder.listFiles();

            StringBuilder builder = new StringBuilder();
            FileReader fileReader;
            BufferedReader bufferedReader;
            FileOutputStream fileOutputStream;

            try {
                for (int i = 0; i < files.length; i++) {
                    fileReader = new FileReader(files[i]);
                    bufferedReader = new BufferedReader(fileReader);

                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        builder.append(line + '\n');
                    }
                }
                fileOutputStream = new FileOutputStream(new File(dir,"new.csv"), false);
                fileOutputStream.write("teamNumber,matchNumber,startingLocation,baseline,autoCubesInSwitch,autoCubesInScale,numberOfCubesInExchange,numberOfCubesInTheirSwitch,numberOfCubesInOpSwitch,NumberOfCubesInScale,cubePickup,climb,canHelpOthersClimb,onPlatform,defense,fouls,scouterInitials".getBytes());
                fileOutputStream.write(builder.toString().getBytes());
                fileOutputStream.close();

            } catch(IOException e) {
                Log.d("Scouting", e.getMessage());
            }
        }
    }

    public void sendMatchData(View view) {
        String file = "storage/emulated/0/Scouting/Match" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv";
        BluetoothUtils.sendFileWithIntent(this, file);
    }

    public void sendPitData(View view) {
        String file = "storage/emulated/0/Scouting/Pit" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv";
        BluetoothUtils.sendFileWithIntent(this, file);
    }

    public void sendRobotPhotos(View view) {
        String dir = "storage/emulated/0/Scouting/Photos/";
        BluetoothUtils.sendFilesWithIntent(this, dir);
    }
}