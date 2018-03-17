package com.example.vande.scouting2018;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;
import utils.StringUtils;
import utils.ViewUtils;

public abstract class ScoutingActivity extends AppCompatActivity implements View.OnKeyListener {
    private ArrayList stringList;
    private HashMap<Integer, TextInputEditText> textInputEditTextMap;
    private HashMap<Integer, TextInputLayout> textInputLayoutMap;
    private HashMap<String, ArrayList<CheckBox>> checkBoxMap;
    private HashMap<Integer, RadioGroup> radioGroupMap;

    private ArrayList<Object> orderedDataList;

    public boolean saveInitials;
    public String previousActivityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previousActivityData = "";

        stringList = new ArrayList<String>();

        textInputLayoutMap = new HashMap<Integer, TextInputLayout>();
        textInputEditTextMap = new HashMap<Integer, TextInputEditText>();
        checkBoxMap = new HashMap<String, ArrayList<CheckBox>>();
        radioGroupMap = new HashMap<Integer, RadioGroup>();

        orderedDataList = new ArrayList<>();

        initialize();
    }

    public void addItem(int i, View view) {
        if(view instanceof TextInputEditText) {
            textInputEditTextMap.put(i, (TextInputEditText)view);
        }
        else if(view instanceof TextInputLayout) {
            textInputLayoutMap.put(i, (TextInputLayout)view);
            orderedDataList.add(view);
        }
        else if(view instanceof RadioGroup) {
            radioGroupMap.put(i, (RadioGroup)view);
            orderedDataList.add(view);
        }
    }

    public void addCheckBoxes(String s, CheckBox... checkBoxes) {
        ArrayList<CheckBox> checkBoxList = new ArrayList(Arrays.asList(checkBoxes));
        checkBoxMap.put(s, checkBoxList);
        orderedDataList.add(checkBoxList);
    }

    public void initialize() {

    }

    private void clearData() {
        clearCheckBoxes();
        clearTextInputs();
        clearRadioGroups();
    }

    protected void clearCheckBoxes() {
        for(ArrayList<CheckBox> list : checkBoxMap.values()) {
            for(CheckBox checkBox : list) {
                checkBox.setChecked(false);
            }
        }
    }

    protected void clearTextInputs() {
        for(TextInputEditText editText : textInputEditTextMap.values()) {
            editText.setText("");
        }
    }

    protected void clearRadioGroups() {
        for(RadioGroup radioGroup : radioGroupMap.values()) {
            radioGroup.clearCheck();
        }
    }

    private boolean canSave() {
        for(TextInputLayout textInputLayout : textInputLayoutMap.values()) {
            if(StringUtils.isEmptyOrNull(getTextInputLayoutString(textInputLayout))) {
                ViewUtils.requestFocus(textInputLayout, this);
                return false;
            }
        }

        for(RadioGroup radioGroup : radioGroupMap.values()) {
            if(radioGroup.getCheckedRadioButtonId() == -1) {
                ViewUtils.requestFocus(radioGroup, this);
                return false;
            }
        }

        return true;
    }

    public void saveData(View view) throws IOException {
        String state = Environment.getExternalStorageState();

        if(PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && canSave()) {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting");
                //create csv file
                File file = new File(dir, "Test" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

                for(Object obj : orderedDataList) {
                    if(obj instanceof ArrayList) {
                        String message = "";
                        for(Object obj1 : (ArrayList)obj) {
                            if(obj1 instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox)obj1;
                                if(checkBox.isChecked()) {
                                    message += checkBox.getText().toString();
                                }
                            }
                        }
                        stringList.add(message);
                    }
                    else if(obj instanceof RadioGroup) {
                        RadioButton button = findViewById(((RadioGroup)obj).getCheckedRadioButtonId());
                        stringList.add(button.getText());
                    }
                    else if(obj instanceof TextInputLayout) {
                        TextInputLayout layout = (TextInputLayout)obj;
                        stringList.add(getTextInputLayoutString(layout));
                    }
                }

                if(saveInitials)
                    stringList.add(ScouterInitialsActivity.getInitials());

                String message = previousActivityData + ',' + FormatStringUtils.addDelimiter(stringList, ",") + "\n";

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
        } else {
            Toast.makeText(getApplicationContext(), "Now that you have permissions, try saving again.", Toast.LENGTH_LONG).show();
        }

        stringList.clear();

        for(TextInputLayout textInputLayout : textInputLayoutMap.values()) {
            textInputLayout.setError(null);
        }
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
            int id = v.getId();

            if (inputEditText != null) {
                textInputEditTextMap.get(id).setError(null);
            }
        }
        return false;
    }

    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();
        for(TextInputEditText editText : textInputEditTextMap.values()) {
            editText.setOnKeyListener(this);
        }
    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();
        for(TextInputEditText editText : textInputEditTextMap.values()) {
            editText.setOnKeyListener(null);
        }
    }

    public String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
