package com.example.vande.scouting2018;

import android.Manifest;
import android.os.Environment;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.design.widget.TextInputLayout;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;
import utils.StringUtils;
import utils.ViewUtils;

import static android.R.attr.value;
import static com.example.vande.scouting2018.AutonActivity.AUTON_STRING_EXTRA;
import static com.example.vande.scouting2018.AutonActivity.MATCH_STRING_EXTRA;
import static com.example.vande.scouting2018.AutonActivity.TEAMNUMBER_STRING_EXTRA;


public class TeleopActivity extends AppCompatActivity implements View.OnKeyListener {
    /*This area sets and binds all of the variables that we will use in the auton activity*/

    @BindView(R.id.teleopCubesInExchange_input_layout)
    public TextInputLayout teleopCubesInExchangeInputLayout;

    @BindView(R.id.teleopCubesInOurSwitch_input_layout)
    public TextInputLayout teleopCubesInOurSwitchInputLayout;

    @BindView(R.id.teleopCubesInTheirSwitch_input_layout)
    public TextInputLayout teleopCubesInTheirSwitchInputLayout;

    @BindView(R.id.teleopCubesInScale_input_layout)
    public TextInputLayout teleopCubesInScaleInputLayout;

    @BindView(R.id.teleopCubesInExchange_input)
    public TextInputEditText teleopCubesInExchangeInput;

    @BindView(R.id.teleopCubesInOurSwitch_input)
    public TextInputEditText teleopCubesInOurSwitchInput;

    @BindView(R.id.teleopCubesInTheirSwitch_input)
    public TextInputEditText teleopCubesInTheirSwitchInput;

    @BindView(R.id.teleopCubesInScale_input)
    public TextInputEditText teleopCubesInScaleInput;

    @BindView(R.id.cubePickupFloor_checkBox)
    public CheckBox cubePickupFloorCheckBox;

    @BindView(R.id.cubePickupPortal_checkBox)
    public CheckBox cubePickupPortalCheckBox;

    @BindView(R.id.fouls_chkbx)
    public CheckBox foulsChbx;

    @BindView(R.id.climb_RadiobtnGrp)
    public RadioGroup climbRadiobtnGrp;

    @BindView(R.id.abilityToHelpClimb_RadiobtnGrp)
    public RadioGroup abilityToHelpClimbRadiobtnGrp;

    @BindView(R.id.onPlatform_RadiobtnGrp)
    public RadioGroup onPlatformRadiobtnGrp;

    @BindView(R.id.defense_RadiobtnGrp)
    public RadioGroup defenseRadiobtnGrp;

    @BindView(R.id.save_btn)
    public Button saveBtn;

    int teleopCubesInExchange = 0;
    int teleopCubesInOurSwitch = 0;
    int teleopCubesInTheirSwitch = 0;
    int teleopCubesInScale = 0;
    public String auton;
    public String matchNumber;
    public String teamNumber;

    private ArrayList<CharSequence> teleopDataStringList;

    /*When this activity is first called,
 *we will call the activity_auton layout so we can display
 *the user interface
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        auton = bundle.getString(AUTON_STRING_EXTRA);
        matchNumber = bundle.getString(MATCH_STRING_EXTRA);
        teamNumber = bundle.getString(TEAMNUMBER_STRING_EXTRA);

        getSupportActionBar().setTitle("Match: " + matchNumber + " - Team: " + teamNumber);

        teleopDataStringList = new ArrayList<>();

        displayTeleopCubesInExchangeInput(teleopCubesInExchange);
        displayTeleopCubesInOurSwitchInput(teleopCubesInOurSwitch);
        displayTeleopCubesInTheirSwitchInput(teleopCubesInTheirSwitch);
        displayTeleopCubesInScaleInput(teleopCubesInScale);
    }

    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();

        teleopCubesInExchangeInput.setOnKeyListener(this);
        teleopCubesInOurSwitchInput.setOnKeyListener(this);
        teleopCubesInTheirSwitchInput.setOnKeyListener(this);
        teleopCubesInScaleInput.setOnKeyListener(this);
    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();

        teleopCubesInExchangeInput.setOnKeyListener(null);
        teleopCubesInOurSwitchInput.setOnKeyListener(null);
        teleopCubesInTheirSwitchInput.setOnKeyListener(null);
        teleopCubesInScaleInput.setOnKeyListener(null);
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

    //Teleop Cube Exchange
    public void decreaseTeleopCubesInExchangeInput(View view) {
        if (teleopCubesInExchange != 0) {
            teleopCubesInExchange = teleopCubesInExchange - 1;
            displayTeleopCubesInExchangeInput(teleopCubesInExchange);
        } else {
        }
    }

    public void increaseTeleopCubesInExchangeInput(View view) {
        teleopCubesInExchange = teleopCubesInExchange + 1;
        displayTeleopCubesInExchangeInput(teleopCubesInExchange);
    }

    private void displayTeleopCubesInExchangeInput(int number) {
        teleopCubesInExchangeInput.setText("" + number);
    }

    //Teleop cubes in our switch
    public void decreaseTeleopCubesInOurSwitchInput(View view) {
        if (teleopCubesInOurSwitch != 0) {
            teleopCubesInOurSwitch = teleopCubesInOurSwitch - 1;
            displayTeleopCubesInOurSwitchInput(teleopCubesInOurSwitch);
        } else {
        }
    }

    public void increaseTeleopCubesInOurSwitchInput(View view) {
        teleopCubesInOurSwitch = teleopCubesInOurSwitch + 1;
        displayTeleopCubesInOurSwitchInput(teleopCubesInOurSwitch);

    }


    private void displayTeleopCubesInOurSwitchInput(int number) {
        teleopCubesInOurSwitchInput.setText("" + number);
    }

    //Teleop cubes in their switch
    public void decreaseTeleopCubesInTheirSwitchInput(View view) {
        if (teleopCubesInTheirSwitch != 0) {
            teleopCubesInTheirSwitch = teleopCubesInTheirSwitch - 1;
            displayTeleopCubesInTheirSwitchInput(teleopCubesInTheirSwitch);
        } else {
        }
    }

    public void increaseTeleopCubesInTheirSwitchInput(View view) {
        teleopCubesInTheirSwitch = teleopCubesInTheirSwitch + 1;
        displayTeleopCubesInTheirSwitchInput(teleopCubesInTheirSwitch);

    }

    private void displayTeleopCubesInTheirSwitchInput(int number) {
        teleopCubesInTheirSwitchInput.setText("" + number);
    }

    //Teleop cubes in our scale
    public void decreaseTeleopCubesInScaleInput(View view) {
        if (teleopCubesInScale != 0) {
            teleopCubesInScale = teleopCubesInScale - 1;
            displayTeleopCubesInScaleInput(teleopCubesInScale);
        } else {
        }
    }

    public void increaseTeleopCubesInScaleInput(View view) {
        teleopCubesInScale = teleopCubesInScale + 1;
        displayTeleopCubesInScaleInput(teleopCubesInScale);
    }

    private void displayTeleopCubesInScaleInput(int number) {
        teleopCubesInScaleInput.setText("" + number);
    }


    /*This method will look at all of the text/number input fields and set error
    *for validation of data entry
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {

                    case R.id.teleopCubesInExchange_input:
                        teleopCubesInExchangeInputLayout.setError(null);
                        break;

                    case R.id.teleopCubesInOurSwitch_input:
                        teleopCubesInOurSwitchInputLayout.setError(null);
                        break;

                    case R.id.teleopCubesInTheirSwitch_input:
                        teleopCubesInTheirSwitchInputLayout.setError(null);
                        break;

                    case R.id.teleopCubesInScale_input:
                        teleopCubesInScaleInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }

    /*
    * This method will verify that all fields are filled and highlight error to user
    * along with change focus to first blank input area. The radio button values are obtained
    * A file is created on the dvice to send the data to. We add the teleop data to the arraylist
    * delimited by commas. We create our message by concatenating the telop data to the end of
    * the auton data. The data is then output to the file we created. We send a message to the user
    * about the saved message. We send a result back to the auton activity upon completion.
    * We then clear the data of the teleop activity and finish it to close and return
    * to the auton activty to clear its data*/
    public void saveData(View view) throws IOException {
        String state = Environment.getExternalStorageState();
        boolean allInputsPassed = false;

        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopCubesInExchangeInputLayout))) {
            teleopCubesInExchangeInputLayout.setError(getText(R.string.teleopCubesInExchangeError));
            ViewUtils.requestFocus(teleopCubesInExchangeInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopCubesInOurSwitchInputLayout))) {
            teleopCubesInOurSwitchInputLayout.setError(getText(R.string.cubesInOurSwitchError));
            ViewUtils.requestFocus(teleopCubesInOurSwitchInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopCubesInTheirSwitchInputLayout))) {
            teleopCubesInTheirSwitchInputLayout.setError(getText(R.string.cubesInTheirSwitchError));
            ViewUtils.requestFocus(teleopCubesInTheirSwitchInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopCubesInScaleInputLayout))) {
            teleopCubesInScaleInputLayout.setError(getText(R.string.cubesInScaleError));
            ViewUtils.requestFocus(teleopCubesInScaleInputLayout, this);
        } else if (climbRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(climbRadiobtnGrp, this);
        } else if (defenseRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(defenseRadiobtnGrp, this);
        } else if (abilityToHelpClimbRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(abilityToHelpClimbRadiobtnGrp, this);
        } else if (onPlatformRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(onPlatformRadiobtnGrp, this);
        } else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }

        final String cubePickup = (cubePickupFloorCheckBox.isChecked() ? "Floor" : "") +
                                    (cubePickupPortalCheckBox.isChecked() ? "Portal" : "");
        final RadioButton climb_Radiobtn = findViewById(climbRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton abilityToHelpClimb_Radiobtn = findViewById(abilityToHelpClimbRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton onPlatform_Radiobtn = findViewById(onPlatformRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton defense_Radiobtn = findViewById(defenseRadiobtnGrp.getCheckedRadioButtonId());

        if(PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting");
                dir.mkdirs();

                File file = new File(dir, "Match" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

                teleopDataStringList.add(getTextInputLayoutString(teleopCubesInExchangeInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopCubesInOurSwitchInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopCubesInTheirSwitchInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopCubesInScaleInputLayout));

                teleopDataStringList.add(cubePickup);
                teleopDataStringList.add(climb_Radiobtn.getText());
                teleopDataStringList.add(abilityToHelpClimb_Radiobtn.getText());
                teleopDataStringList.add(onPlatform_Radiobtn.getText());
                teleopDataStringList.add(defense_Radiobtn.getText());

                teleopDataStringList.add(String.valueOf(foulsChbx.isChecked()));

                teleopDataStringList.add(ScouterInitialsActivity.getInitials());

                String message = auton + "," + FormatStringUtils.addDelimiter(teleopDataStringList, ",") + "\n";

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

            Intent intent = getIntent();
            intent.putExtra("Key", value);
            setResult(RESULT_OK, intent);

            clearData(view);
            finish();
        }

        teleopCubesInExchangeInputLayout.setError(null);
        teleopCubesInOurSwitchInputLayout.setError(null);
        teleopCubesInTheirSwitchInputLayout.setError(null);
        teleopCubesInScaleInputLayout.setError(null);
    }

    /*The method will clear all the data in the text fields, checkboxes, and
    * set radio buttons to default*/
    public void clearData(View view) {
        teleopCubesInExchangeInput.setText("" + teleopCubesInExchange);
        teleopCubesInOurSwitchInput.setText("" + teleopCubesInOurSwitch);
        teleopCubesInTheirSwitchInput.setText("" + teleopCubesInTheirSwitch);
        teleopCubesInScaleInput.setText("" + teleopCubesInScale);

        cubePickupPortalCheckBox.setChecked(false);
        cubePickupFloorCheckBox.setChecked(false);

        climbRadiobtnGrp.clearCheck();
        abilityToHelpClimbRadiobtnGrp.clearCheck();
        onPlatformRadiobtnGrp.clearCheck();
        defenseRadiobtnGrp.clearCheck();
    }

    /* This method will change the text entered into the app into a string if it is not already*/
    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
