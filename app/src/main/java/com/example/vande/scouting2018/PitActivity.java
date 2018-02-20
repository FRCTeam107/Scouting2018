package com.example.vande.scouting2018;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;
import utils.StringUtils;
import utils.ViewUtils;

/**
 * Created by Matt on 9/30/2017.
 */

public class PitActivity extends AppCompatActivity implements View.OnKeyListener {
    @BindView(R.id.pit_teamNumber_input_layout)
    public TextInputLayout pitTeamNumberInputLayout;

    @BindView(R.id.pit_teamNumber_input)
    public TextInputEditText pitTeamNumberInput;

    @BindView(R.id.pit_vaultPriority_input_layout)
    public TextInputLayout pitVaultPriorityInputLayout;

    @BindView(R.id.pit_vaultPriority_input)
    public TextInputEditText pitVaultPriorityInput;

    @BindView(R.id.pit_startingPosition_RadiobtnGrp)
    public RadioGroup pitStartingPositionRadiobtnGrp;

    @BindView(R.id.pit_teleopPreference_RadiobtnGrp)
    public RadioGroup pitTeleopPreferenceRadiobtnGrp;

    @BindView(R.id.pit_defenseType_RadiobtnGrp)
    public RadioGroup pitDefenseTypeRadiobtnGrp;

    @BindView(R.id.pit_pickupBoolean_RadiobtnGrp)
    public RadioGroup pitPickUpOffFloorRadioGrp;

    @BindView(R.id.pit_cubeNumberInSwitch_input_layout)
    public TextInputLayout pitCubeNumberInSwitchInputLayout;

    @BindView(R.id.pit_cubeNumberInScale_input_layout)
    public TextInputLayout pitCubeNumberInScaleInputLayout;

    @BindView(R.id.pit_cubeNumberInExchange_input_layout)
    public TextInputLayout pitCubeNumberInExchangeInputLayout;

    @BindView(R.id.pit_cubeNumberInSwitch_input)
    public TextInputEditText pitCubeNumberInSwitchInput;

    @BindView(R.id.pit_cubeNumberInScale_input)
    public TextInputEditText pitCubeNumberInScaleInput;

    @BindView(R.id.pit_cubeNumberInExchange_input)
    public TextInputEditText pitCubeNumberInExchangeInput;

    //TODO:Change this to checkbox???
    @BindView(R.id.pit_climbBoolean_RadiobtnGrp)
    public RadioGroup pitClimbBooleanRadiobtnGrp;

    @BindView(R.id.pit_climbHelpBoolean_RadiobtnGrp)
    public RadioGroup pitCanHelpClimbRadioGrp;

    @BindView(R.id.pit_programmingLanguage_RadiobtnGrp)
    public RadioGroup pitProgrammingLanguageRadiobtnGrp;

    @BindView(R.id.pit_arcadeGame_input_layout)
    public TextInputLayout pitArcadeGameInputLayout;

    @BindView(R.id.pit_arcadeGame_input)
    public TextInputEditText pitArcadeGameInput;

    @BindView(R.id.save_pit_btn)
    public Button savePitBtn;


    private ArrayList<CharSequence> pitDataStringList;
//    private ArrayList<CharSequence> headingDataStringList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pit);
        pitDataStringList = new ArrayList<>();

        ButterKnife.bind(this);

        checkForPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

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

    @Override
    protected void onResume() {
        super.onResume();

        pitTeamNumberInputLayout.setOnKeyListener(this);
        pitCubeNumberInSwitchInputLayout.setOnKeyListener(this);
        pitCubeNumberInScaleInputLayout.setOnKeyListener(this);
        pitCubeNumberInExchangeInputLayout.setOnKeyListener(this);
        pitArcadeGameInputLayout.setOnKeyListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();

        pitTeamNumberInputLayout.setOnKeyListener(null);
        pitCubeNumberInSwitchInputLayout.setOnKeyListener(null);
        pitCubeNumberInScaleInputLayout.setOnKeyListener(null);
        pitCubeNumberInExchangeInputLayout.setOnKeyListener(null);
        pitArcadeGameInputLayout.setOnKeyListener(null);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {

                    case R.id.pit_teamNumber_input:
                        pitTeamNumberInputLayout.setError(null);
                        break;

                    case R.id.pit_cubeNumberInSwitch_input:
                        pitCubeNumberInSwitchInputLayout.setError(null);
                        break;

                    case R.id.pit_cubeNumberInScale_input:
                        pitCubeNumberInScaleInputLayout.setError(null);
                        break;

                    case R.id.pit_cubeNumberInExchange_input:
                        pitCubeNumberInExchangeInputLayout.setError(null);
                        break;

                    case R.id.pit_vaultPriority_input:
                        pitVaultPriorityInputLayout.setError(null);
                        break;

                    case R.id.pit_arcadeGame_input:
                        pitArcadeGameInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }

    public void savePitData(View view) throws IOException {
        String state = Environment.getExternalStorageState();
        boolean allInputsPassed = false;

        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitTeamNumberInputLayout)) || Integer.valueOf(getTextInputLayoutString(pitTeamNumberInputLayout)) == 0) {
            pitTeamNumberInputLayout.setError(getText(R.string.pitTeamNumberError));
            ViewUtils.requestFocus(pitTeamNumberInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitCubeNumberInSwitchInputLayout))) {
            pitCubeNumberInSwitchInputLayout.setError(getText(R.string.pitCubeNumberInSwitchError));
            ViewUtils.requestFocus(pitCubeNumberInSwitchInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitCubeNumberInScaleInputLayout))) {
            pitCubeNumberInScaleInputLayout.setError(getText(R.string.pitCubeNumberInScaleError));
            ViewUtils.requestFocus(pitCubeNumberInScaleInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitCubeNumberInExchangeInputLayout))) {
            pitCubeNumberInExchangeInputLayout.setError(getText(R.string.pitCubeNumberInExchangeError));
            ViewUtils.requestFocus(pitCubeNumberInExchangeInputLayout, this);
        } else if (pitStartingPositionRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitStartingPositionRadiobtnGrp, this);
        } else if (pitTeleopPreferenceRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitTeleopPreferenceRadiobtnGrp, this);
        } else if (pitDefenseTypeRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitDefenseTypeRadiobtnGrp, this);
        } else if (pitClimbBooleanRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitClimbBooleanRadiobtnGrp, this);
        } else if (pitCanHelpClimbRadioGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitCanHelpClimbRadioGrp, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitVaultPriorityInputLayout))) {
            pitVaultPriorityInputLayout.setError(getText(R.string.pitVaultPriorityError));
            ViewUtils.requestFocus(pitVaultPriorityInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitArcadeGameInputLayout))) {
            pitArcadeGameInputLayout.setError(getText(R.string.pitArcadeGameError));
            ViewUtils.requestFocus(pitArcadeGameInputLayout, this);
        } else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }

        final RadioButton pitStaring_Radiobtn = findViewById(pitStartingPositionRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitTeleopPreference_Radiobtn = findViewById(pitTeleopPreferenceRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitDefenseType_Radiobtn = findViewById(pitDefenseTypeRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitClimbBoolean_Radiobtn = findViewById(pitClimbBooleanRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitPickUpOffFloor_Radiobtn = findViewById(pitPickUpOffFloorRadioGrp.getCheckedRadioButtonId());
        final RadioButton pitCanHelpClimb_Radiobtn = findViewById(pitCanHelpClimbRadioGrp.getCheckedRadioButtonId());
        final RadioButton pitProgrammingLanguage_Radiobtn = findViewById(pitProgrammingLanguageRadiobtnGrp.getCheckedRadioButtonId());

        if(PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting");
                //create csv file
                File file = new File(dir, "Pit" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

                pitDataStringList.add(getTextInputLayoutString(pitTeamNumberInputLayout));
                pitDataStringList.add(pitStaring_Radiobtn.getText());
                pitDataStringList.add(pitTeleopPreference_Radiobtn.getText());
                pitDataStringList.add(pitDefenseType_Radiobtn.getText());
                pitDataStringList.add(getTextInputLayoutString(pitCubeNumberInSwitchInputLayout));
                pitDataStringList.add(getTextInputLayoutString(pitCubeNumberInScaleInputLayout));
                pitDataStringList.add(getTextInputLayoutString(pitCubeNumberInExchangeInputLayout));
                pitDataStringList.add(pitPickUpOffFloor_Radiobtn.getText());
                pitDataStringList.add(getTextInputLayoutString(pitVaultPriorityInputLayout));
                pitDataStringList.add(pitClimbBoolean_Radiobtn.getText());
                pitDataStringList.add(pitCanHelpClimb_Radiobtn.getText());
                pitDataStringList.add(pitProgrammingLanguage_Radiobtn.getText());
                pitDataStringList.add(getTextInputLayoutString(pitArcadeGameInputLayout));


                String message = FormatStringUtils.addDelimiter(pitDataStringList, ",") + "\n";


                //Output data to file
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    fileOutputStream.write(message.getBytes());
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "IOException! Go talk to the programmers!", Toast.LENGTH_LONG).show();
                    Log.d("Scouting", e.getMessage());
                }
            } else {
                Toast.makeText(getApplicationContext(), "SD card not found", Toast.LENGTH_LONG).show();
            }

            clearData();
            pitTeamNumberInput.requestFocus();
        }
    }

    public void takePhoto(View view) {
        String name = getTextInputLayoutString(pitTeamNumberInputLayout);

        if(PermissionUtils.getPermissions(this, Manifest.permission.CAMERA) &&
                PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (!StringUtils.isEmptyOrNull(name)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting/Photos");
                dir.mkdirs();

                File file = new File(dir, name + ".jpg");

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.d("Scouting", e.getMessage());
                }

                Uri outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                    startActivityForResult(takePictureIntent, 1);
                }

                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    Bitmap bitmap;

                    while ((bitmap = BitmapFactory.decodeStream(inputStream)) == null) {
                    }

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);

                    FileOutputStream outputStream = new FileOutputStream(new File(dir, name + ".jpg"));
                    outputStream.write(out.toByteArray());
                    inputStream.close();
                    out.close();
                    outputStream.close();
                } catch (IOException e) {
                    Log.d("Scouting", e.getMessage());
                }
            } else {
                pitTeamNumberInputLayout.setError(getText(R.string.pitTeamNumberError));
                ViewUtils.requestFocus(pitTeamNumberInputLayout, this);
            }
        }
        clearData();
    }

    public void clearData() {
        pitTeamNumberInput.setText("");

        pitStartingPositionRadiobtnGrp.clearCheck();
        pitTeleopPreferenceRadiobtnGrp.clearCheck();
        pitDefenseTypeRadiobtnGrp.clearCheck();

        pitCubeNumberInSwitchInput.setText("");
        pitCubeNumberInScaleInput.setText("");
        pitCubeNumberInExchangeInput.setText("");

        pitPickUpOffFloorRadioGrp.clearCheck();

        pitVaultPriorityInput.setText("");

        pitCanHelpClimbRadioGrp.clearCheck();
        pitCanHelpClimbRadioGrp.clearCheck();

        pitProgrammingLanguageRadiobtnGrp.clearCheck();

        pitArcadeGameInput.setText("");
    }

    private void checkForPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
