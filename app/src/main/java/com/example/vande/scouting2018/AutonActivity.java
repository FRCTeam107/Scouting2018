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

public class AutonActivity extends AppCompatActivity implements View.OnKeyListener {

    /*This area sets and binds all of the variables that we will use in the auton activity*/
    public static String AUTON_STRING_EXTRA = "auton_extra";

    /* These are the names of the match number and team number extras that will be passed into teleop */
    public static final String MATCH_STRING_EXTRA = "match_extra";
    public static final String TEAMNUMBER_STRING_EXTRA = "teamnumber_extra";

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

    private ArrayList<CharSequence> autonDataStringList;
    public static final int REQUEST_CODE = 1;


    /*When this activity is first called,
     *we will call the activity_auton layout so we can display
     *the user interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auton);
        ButterKnife.bind(this);
        autonDataStringList = new ArrayList<>();

        checkForPermissions();
    }

    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();

        autonDataStringList.clear();

        teamNumberInput.setOnKeyListener(this);
        matchNumberInput.setOnKeyListener(this);
        cubeInSwitchRadiobtnGrp.setOnKeyListener(this);
        cubeInScaleRadiobtnGrp.setOnKeyListener(this);
    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();

        teamNumberInput.setOnKeyListener(null);
        matchNumberInput.setOnKeyListener(null);
        cubeInSwitchRadiobtnGrp.setOnKeyListener(null);
        cubeInScaleRadiobtnGrp.setOnKeyListener(null);
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


    /*This method will look at all of the text/number input fields and set error
    *for validation of data entry
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {

                    case R.id.teamNumber_input:
                        teamNumberInputLayout.setError(null);
                        break;

                    case R.id.matchNumber_input:
                        matchNumberInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
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

        autonDataStringList.add(getTextInputLayoutString(teamNumberInputLayout));
        autonDataStringList.add(getTextInputLayoutString(matchNumberInputLayout));
        autonDataStringList.add(startingLocation_Radiobtn.getText());
        autonDataStringList.add(baseline_Radiobtn.getText());
        autonDataStringList.add(cubeInSwitch_Radiobtn.getText());
        autonDataStringList.add(cubeInScale_Radiobtn.getText());

        final Intent intent = new Intent(this, TeleopActivity.class);
        intent.putExtra(AUTON_STRING_EXTRA, FormatStringUtils.addDelimiter(autonDataStringList, ","));
        intent.putExtra(MATCH_STRING_EXTRA, getTextInputLayoutString(matchNumberInputLayout));
        intent.putExtra(TEAMNUMBER_STRING_EXTRA, getTextInputLayoutString(teamNumberInputLayout));

        startActivityForResult(intent, REQUEST_CODE);

        teamNumberInputLayout.setError(null);
        matchNumberInputLayout.setError(null);

        matchNumberInput.requestFocus();
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

    /*This method will clear all of the text entry fields as well
    * as reset the checkboxes and reset the radio buttons to their default*/
    public void clearData() {
        teamNumberInput.setText("");
        matchNumberInput.setText("");
        startingLocationRadiobtnGrp.clearCheck();
        baseLineRadiobtnGrp.clearCheck();
        cubeInSwitchRadiobtnGrp.clearCheck();
        cubeInScaleRadiobtnGrp.clearCheck();
    }


    /* This method will change the text entered into the app into a string if it is not already*/
    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }

    private void checkForPermissions() {
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

}
