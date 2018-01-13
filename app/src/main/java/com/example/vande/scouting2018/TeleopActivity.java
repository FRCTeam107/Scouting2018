package com.example.vande.scouting2018;

import android.os.Environment;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import utils.StringUtils;
import utils.ViewUtils;

import static android.R.attr.value;
import static com.example.vande.scouting2018.AutonActivity.AUTON_STRING_EXTRA;


public class TeleopActivity extends AppCompatActivity implements View.OnKeyListener {
    /*This area sets and binds all of the variables that we will use in the auton activity*/
    @BindView(R.id.teleopGearPlaced_input_layout)
    public TextInputLayout teleopGearPlacedInputLayout;

    @BindView(R.id.teleopGearDropped_input_layout)
    public TextInputLayout teleopGearDroppedInputLayout;

    @BindView(R.id.teleopHighFuelScored_input_layout)
    public TextInputLayout teleopHighFuelScoredInputLayout;

    @BindView(R.id.teleopHighFuelMissed_input_layout)
    public TextInputLayout teleopHighFuelMissedInputLayout;

    @BindView(R.id.teleopLowFuel_input_layout)
    public TextInputLayout teleopLowFuelInputLayout;

    @BindView(R.id.climbTime_input_layout)
    public TextInputLayout climbTimeInputLayout;

    @BindView(R.id.teleopGearPlaced_input)
    public TextInputEditText teleopGearPlacedInput;

    @BindView(R.id.teleopGearDropped_input)
    public TextInputEditText teleopGearDroppedInput;

    @BindView(R.id.teleopHighFuelScored_input)
    public TextInputEditText teleopHighFuelScoredInput;

    @BindView(R.id.teleopHighFuelMissed_input)
    public TextInputEditText teleopHighFuelMissedInput;

    @BindView(R.id.teleopLowFuel_input)
    public TextInputEditText teleopLowFuelInput;

    @BindView(R.id.climbTime_input)
    public TextInputEditText climbTimeInput;

    @BindView(R.id.gearPlacement_RadiobtnGrp)
    public RadioGroup gearPlacementRadiobtnGrp;

    @BindView(R.id.fuelRetrieval_RadiobtnGrp)
    public RadioGroup fuelRetrievalRadiobtnGrp;

    @BindView(R.id.gearRetrieval_RadiobtnGrp)
    public RadioGroup gearRetrievalRadiobtnGrp;

    @BindView(R.id.climbing_RadiobtnGrp)
    public RadioGroup climbingRadiobtnGrp;

    @BindView(R.id.defense_RadiobtnGrp)
    public RadioGroup defenseRadiobtnGrp;

    @BindView(R.id.save_btn)
    public Button saveBtn;

    @BindView(R.id.fouls_chkbx)
    public CheckBox foulsChbx;

    public String auton;

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

        teleopDataStringList = new ArrayList<>();
    }

    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();

        teleopGearPlacedInput.setOnKeyListener(this);
        teleopGearDroppedInput.setOnKeyListener(this);
        teleopHighFuelScoredInput.setOnKeyListener(this);
        teleopHighFuelMissedInput.setOnKeyListener(this);
        climbTimeInput.setOnKeyListener(this);

    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();

        teleopGearPlacedInput.setOnKeyListener(null);
        teleopGearDroppedInput.setOnKeyListener(null);
        teleopHighFuelScoredInput.setOnKeyListener(null);
        teleopHighFuelMissedInput.setOnKeyListener(null);
        climbTimeInput.setOnKeyListener(null);
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
            case R.id.match_scouting:
                startActivity(new Intent(this, AutonActivity.class));
                return true;
            case R.id.pit_scouting:
                startActivity(new Intent(this, PitActivity.class));
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

                    case R.id.teleopGearPlaced_input:
                        teleopGearPlacedInputLayout.setError(null);
                        break;

                    case R.id.teleopGearDropped_input:
                        teleopGearDroppedInputLayout.setError(null);
                        break;

                    case R.id.teleopHighFuelScored_input:
                        teleopHighFuelScoredInputLayout.setError(null);
                        break;

                    case R.id.teleopHighFuelMissed_input:
                        teleopHighFuelMissedInputLayout.setError(null);
                        break;

                    case R.id.climbTime_input:
                        climbTimeInputLayout.setError(null);
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

        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopGearPlacedInputLayout))) {
            teleopGearPlacedInputLayout.setError(getText(R.string.teleopGearPlacedError));
            ViewUtils.requestFocus(teleopGearPlacedInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopGearDroppedInputLayout))) {
            teleopGearDroppedInputLayout.setError(getText(R.string.teleopGearDroppedError));
            ViewUtils.requestFocus(teleopGearDroppedInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopHighFuelScoredInputLayout))) {
            teleopHighFuelScoredInputLayout.setError(getText(R.string.teleopHighFuelScoredError));
            ViewUtils.requestFocus(teleopHighFuelScoredInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopHighFuelMissedInputLayout))) {
            teleopHighFuelMissedInputLayout.setError(getText(R.string.teleopHighFuelMissedError));
            ViewUtils.requestFocus(teleopHighFuelMissedInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopLowFuelInputLayout))) {
            teleopLowFuelInputLayout.setError(getText(R.string.teleopLowFuelError));
            ViewUtils.requestFocus(teleopLowFuelInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(climbTimeInputLayout))) {
            climbTimeInputLayout.setError(getText(R.string.climbTimeError));
            ViewUtils.requestFocus(climbTimeInputLayout, this);
        } else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }

        final RadioButton gearPlacement_Radiobtn = (RadioButton) findViewById(gearPlacementRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton fuelRetreival_Radiobtn = (RadioButton) findViewById(fuelRetrievalRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton gearRetreival_Radiobtn = (RadioButton) findViewById(gearRetrievalRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton climbing_Radiobtn = (RadioButton) findViewById(climbingRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton defense_Radiobtn = (RadioButton) findViewById(defenseRadiobtnGrp.getCheckedRadioButtonId());

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsoluteFile() + "/Documents");
            File file = new File(Dir, "Match" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

            teleopDataStringList.add(getTextInputLayoutString(teleopGearPlacedInputLayout));
            teleopDataStringList.add(gearPlacement_Radiobtn.getText());
            teleopDataStringList.add(getTextInputLayoutString(teleopGearDroppedInputLayout));
            teleopDataStringList.add(getTextInputLayoutString(teleopHighFuelScoredInputLayout));
            teleopDataStringList.add(getTextInputLayoutString(teleopHighFuelMissedInputLayout));
            teleopDataStringList.add(getTextInputLayoutString(teleopLowFuelInputLayout));
            teleopDataStringList.add(fuelRetreival_Radiobtn.getText());
            teleopDataStringList.add(gearRetreival_Radiobtn.getText());
            teleopDataStringList.add(climbing_Radiobtn.getText());
            teleopDataStringList.add(getTextInputLayoutString(climbTimeInputLayout));
            teleopDataStringList.add(defense_Radiobtn.getText());

            String message = auton + "," + FormatStringUtils.addDelimiter(teleopDataStringList, ",");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "SD card not found", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), "message Saved", Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        intent.putExtra("Key", value);
        setResult(RESULT_OK, intent);

        clearData(view);
        finish();
    }

    /*The method will clear all the data in the text fields, checkboxes, and
    * set radio buttons to default*/
    public void clearData(View view) {
        teleopGearPlacedInput.setText("");
        gearPlacementRadiobtnGrp.check(R.id.passiveGearPlacement_btn);
        teleopGearDroppedInput.setText("");
        teleopHighFuelScoredInput.setText("");
        teleopHighFuelMissedInput.setText("");
        teleopLowFuelInput.setText("");
        fuelRetrievalRadiobtnGrp.check(R.id.noFuelRetrieval_btn);
        gearRetrievalRadiobtnGrp.check(R.id.noGearRetrieval_btn);
        climbingRadiobtnGrp.check(R.id.failClimb_btn);
        climbTimeInput.setText("");
        defenseRadiobtnGrp.check(R.id.noDefense_btn);
    }

    /* This method will change the text entered into the app into a string if it is not already*/
    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
