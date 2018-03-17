package com.example.vande.scouting2018;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.StringUtils;
import utils.ViewUtils;

public class AutonActivity extends ScoutingActivity {

    /*This area sets and binds all of the variables that we will use in the auton activity*/
    public static String AUTON_STRING_EXTRA = "auton_extra";

    @BindView(R.id.teamNumber_input_layout)
    public TextInputLayout teamNumberInputLayout;

    @BindView(R.id.matchNumber_input_layout)
    public TextInputLayout matchNumberInputLayout;

    @BindView(R.id.teamNumber_input)
    public EditText teamNumberInput;

    @BindView(R.id.matchNumber_input)
    public EditText matchNumberInput;

    @BindView(R.id.startingLocation_RadiobtnGrp)
    public RadioGroup startingLocationRadiobtnGrp;

    @BindView(R.id.baseLine_RadiobtnGrp)
    public RadioGroup baseLineRadiobtnGrp;

    @BindView(R.id.cubeInSwitch_RadiobtnGrp)
    public RadioGroup cubeInSwitchRadiobtnGrp;

    @BindView(R.id.cubeInScale_RadiobtnGrp)
    public RadioGroup cubeInScaleRadiobtnGrp;

    @BindView(R.id.next_button)
    public Button nextButton;

    public static final int REQUEST_CODE = 1;

    @Override
    public void initialize() {
        setContentView(R.layout.activity_auton);
        ButterKnife.bind(this);

        addItem(R.id.teamNumber_input_layout, teamNumberInputLayout);
        addItem(R.id.teamNumber_input, teamNumberInput);
        addItem(R.id.matchNumber_input_layout, matchNumberInputLayout);
        addItem(R.id.matchNumber_input, matchNumberInput);

        addItem(R.id.startingLocation_RadiobtnGrp, startingLocationRadiobtnGrp);
        addItem(R.id.baseLine_RadiobtnGrp, baseLineRadiobtnGrp);
        addItem(R.id.cubeInSwitch_RadiobtnGrp, cubeInSwitchRadiobtnGrp);
        addItem(R.id.cubeInScale_RadiobtnGrp, cubeInScaleRadiobtnGrp);

        checkForPermissions();
    }


    /*This method takes place when the Show teleop button is pressed
    *This will first check if the text fields are empty and highlight
    * The area not completed as well as put that text input as the focus
    * to the user in the app. If everything passes by being filled in,
    * The value of the radio buttons will be obtained.
    * Then all of the values of this activity are added to the autonDataStringList
    * delimited by a comma. This method will then launch the teleop activity while sending
    * over our list of data. A request on result is requested so we can clear this aplication
    * after the teleop activity closes
     */
    public void onShowTeleop(View view) {
        boolean allInputsPassed = false;

        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teamNumberInputLayout)) || Integer.valueOf(getTextInputLayoutString(teamNumberInputLayout)) == 0) {
            teamNumberInputLayout.setError(getText(R.string.teamNumberError));
            ViewUtils.requestFocus(teamNumberInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(matchNumberInputLayout)) || Integer.valueOf(getTextInputLayoutString(matchNumberInputLayout)) == 0) {
            matchNumberInputLayout.setError(getText(R.string.matchNumberError));
            ViewUtils.requestFocus(matchNumberInputLayout, this);
        } else if (baseLineRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(baseLineRadiobtnGrp, this);
        } else if (cubeInScaleRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(cubeInScaleRadiobtnGrp, this);
        } else if (cubeInSwitchRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(cubeInSwitchRadiobtnGrp, this);
        } else if (startingLocationRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(startingLocationRadiobtnGrp, this);
        } else {
            allInputsPassed = true;
        }

        if (!allInputsPassed) {
            return;
        }

        final RadioButton startingLocation_Radiobtn = findViewById(startingLocationRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton baseline_Radiobtn = findViewById(baseLineRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton cubeInSwitch_Radiobtn = findViewById(cubeInSwitchRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton cubeInScale_Radiobtn = findViewById(cubeInScaleRadiobtnGrp.getCheckedRadioButtonId());

        stringList.add(getTextInputLayoutString(teamNumberInputLayout));
        stringList.add(getTextInputLayoutString(matchNumberInputLayout));
        stringList.add(startingLocation_Radiobtn.getText());
        stringList.add(baseline_Radiobtn.getText());
        stringList.add(cubeInSwitch_Radiobtn.getText());
        stringList.add(cubeInScale_Radiobtn.getText());

        teamNumberInputLayout.setError(null);
        matchNumberInputLayout.setError(null);

        final Intent intent = new Intent(this, TeleopActivity.class);
        intent.putExtra(AUTON_STRING_EXTRA, FormatStringUtils.addDelimiter(stringList, ","));

        startActivityForResult(intent, REQUEST_CODE);
    }

    /*This method will check for the result code from the teleop activity
     *so we can clear the data before the next match scouting starts
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                clearData();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkForPermissions() {
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

}
