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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class PitActivity extends ScoutingActivity {
    @BindView(R.id.pit_teamNumber_input_layout)
    public TextInputLayout pitTeamNumberInputLayout;

    @BindView(R.id.pit_teamNumber_input)
    public TextInputEditText pitTeamNumberInput;

    @BindView(R.id.pit_vaultPriority_input_layout)
    public TextInputLayout pitVaultPriorityInputLayout;

    @BindView(R.id.pit_vaultPriority_input)
    public TextInputEditText pitVaultPriorityInput;

    @BindView(R.id.pit_startingPosition_layout)
    public LinearLayout pitStartingPositionLayout;

    @BindView(R.id.pitStartingPositionLeft_btn)
    public CheckBox pitStartingPositionLeft;

    @BindView(R.id.pitStartingPositionRight_btn)
    public CheckBox pitStartingPositionRight;

    @BindView(R.id.pitStartingPositionMiddle_btn)
    public CheckBox pitStartingPositionMiddle;

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

    @BindView(R.id.pit_comments_input_layout)
    public TextInputLayout pitCommentInputLayout;

    @BindView(R.id.pit_comments_input)
    public TextInputEditText pitCommentInput;

    @BindView(R.id.save_pit_btn)
    public Button savePitBtn;

    @Override
    public void initialize() {

        addItem(R.id.teamNumber_input_layout, pitTeamNumberInputLayout);
        addItem(R.id.teamNumber_input, pitTeamNumberInput);

        addItem(String.valueOf(R.id.startingLocation_RadiobtnGrp), pitStartingPositionLeft, pitStartingPositionMiddle, pitStartingPositionRight);

        addItem(R.id.pit_teleopPreference_RadiobtnGrp, pitTeleopPreferenceRadiobtnGrp);
        addItem(R.id.defense_RadiobtnGrp, pitDefenseTypeRadiobtnGrp);

        addItem(R.id.pit_cubeNumberInSwitch_input_layout, pitCubeNumberInSwitchInputLayout);
        addItem(R.id.pit_cubeNumberInSwitch_input, pitCubeNumberInSwitchInput);
        addItem(R.id.pit_cubeNumberInScale_input_layout, pitCubeNumberInScaleInputLayout);
        addItem(R.id.pit_cubeNumberInScale_input, pitCubeNumberInScaleInput);
        addItem(R.id.pit_cubeNumberInExchange_input_layout, pitCubeNumberInExchangeInputLayout);
        addItem(R.id.pit_cubeNumberInExchange_input, pitCubeNumberInExchangeInput);

        addItem(R.id.pit_pickupBoolean_RadiobtnGrp, pitPickUpOffFloorRadioGrp);

        addItem(R.id.pit_vaultPriority_input_layout, pitVaultPriorityInputLayout);
        addItem(R.id.pit_vaultPriority_input, pitVaultPriorityInput);

        addItem(R.id.climb_RadiobtnGrp, pitClimbBooleanRadiobtnGrp);
        addItem(R.id.pit_climbHelpBoolean_RadiobtnGrp, pitCanHelpClimbRadioGrp);
        addItem(R.id.pit_programmingLanguage_RadiobtnGrp, pitProgrammingLanguageRadiobtnGrp);

        addItem(R.id.pit_arcadeGame_input_layout, pitArcadeGameInputLayout);
        addItem(R.id.pit_arcadeGame_input, pitArcadeGameInput);
        addItem(R.id.pit_comments_input_layout, pitCommentInputLayout);
        addItem(R.id.pit_comments_input, pitCommentInput);


        /*pitDataStringList.add(getTextInputLayoutString(pitTeamNumberInputLayout));
        pitDataStringList.add(startingPositions);
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
        pitDataStringList.add(getTextInputLayoutString(pitCommentInputLayout));*/
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
    }

    private void checkForPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }
}
