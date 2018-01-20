package com.example.vande.scouting2018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.StringUtils;
import utils.ViewUtils;

/**
 * Created by Matt on 9/30/2017.
 */

public class PitActivity extends AppCompatActivity implements View.OnKeyListener {
    @BindView(R.id.pit_teamNumber_input_layout)
    public TextInputLayout pitTeamNumberInputLayout;

    @BindView(R.id.pit_startingPosition_RadiobtnGrp)
    public RadioGroup pitStartingPositionRadiobtnGrp;

    @BindView(R.id.pit_teleopPreference_RadiobtnGrp)
    public RadioGroup pitTeleopPreferenceRadiobtnGrp;

    @BindView(R.id.pit_defenseType_RadiobtnGrp)
    public RadioGroup pitDefenseTypeRadiobtnGrp;

    @BindView(R.id.pit_cubeNumber_input_layout)
    public TextInputLayout pitCubeNumberInputLayout;
    //TODO:Change this to checkbox???
    @BindView(R.id.pit_climbBoolean_RadiobtnGrp)
    public RadioGroup pitClimbBooleanRadiobtnGrp;

    @BindView(R.id.pit_climbTime_input_layout)
    public TextInputLayout pitClimbTimeInputLayout;

    @BindView(R.id.pit_arcadeGame_input_layout)
    public TextInputLayout pitArcadeGameInputLayout;

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

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

    @Override
    protected void onResume() {
        super.onResume();

        pitTeamNumberInputLayout.setOnKeyListener(this);
        pitCubeNumberInputLayout.setOnKeyListener(this);
        pitClimbTimeInputLayout.setOnKeyListener(this);
        pitArcadeGameInputLayout.setOnKeyListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();

        pitTeamNumberInputLayout.setOnKeyListener(null);
        pitCubeNumberInputLayout.setOnKeyListener(null);
        pitClimbTimeInputLayout.setOnKeyListener(null);
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

                    case R.id.pit_cubeNumber_input:
                        pitCubeNumberInputLayout.setError(null);
                        break;

                    case R.id.pit_climbTime_input:
                        pitClimbTimeInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }

    public void savePitData(View view) throws IOException {
        String state = Environment.getExternalStorageState();
        boolean allInputsPassed = false;


        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitTeamNumberInputLayout))) {
            pitTeamNumberInputLayout.setError(getText(R.string.pitTeamNumberError));
            ViewUtils.requestFocus(pitTeamNumberInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitCubeNumberInputLayout))) {
            pitCubeNumberInputLayout.setError(getText(R.string.pitNumberOfGearsError));
            ViewUtils.requestFocus(pitCubeNumberInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(pitClimbTimeInputLayout))) {
            pitClimbTimeInputLayout.setError(getText(R.string.pitClimbTimeError));
            ViewUtils.requestFocus(pitClimbTimeInputLayout, this);
        } else if (pitStartingPositionRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitStartingPositionRadiobtnGrp, this);
        } else if (pitTeleopPreferenceRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitTeleopPreferenceRadiobtnGrp, this);
        } else if (pitDefenseTypeRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitDefenseTypeRadiobtnGrp, this);
        } else if (pitClimbBooleanRadiobtnGrp.getCheckedRadioButtonId() == -1) {
            ViewUtils.requestFocus(pitClimbBooleanRadiobtnGrp, this);
        } else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }

        final RadioButton pitStaring_Radiobtn = (RadioButton) findViewById(pitStartingPositionRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitTeleopPreference_Radiobtn = (RadioButton) findViewById(pitTeleopPreferenceRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitDefenseType_Radiobtn = (RadioButton) findViewById(pitDefenseTypeRadiobtnGrp.getCheckedRadioButtonId());
        final RadioButton pitClimbBoolean_Radiobtn = (RadioButton) findViewById(pitClimbBooleanRadiobtnGrp.getCheckedRadioButtonId());


        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsoluteFile() + "/Documents");
            //create csv file
            File file = new File(Dir, "Pit" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");


            pitDataStringList.add(getTextInputLayoutString(pitTeamNumberInputLayout));
            pitDataStringList.add(pitStaring_Radiobtn.getText());
            pitDataStringList.add(pitTeleopPreference_Radiobtn.getText());
            pitDataStringList.add(pitDefenseType_Radiobtn.getText());
            pitDataStringList.add(getTextInputLayoutString(pitCubeNumberInputLayout));
            pitDataStringList.add(pitClimbBoolean_Radiobtn.getText());
            pitDataStringList.add(getTextInputLayoutString(pitClimbTimeInputLayout));
            pitDataStringList.add(getTextInputLayoutString(pitArcadeGameInputLayout));


            String message = FormatStringUtils.addDelimiter(pitDataStringList, ",");


            //Output data to file
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

        finish();
    }


    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
