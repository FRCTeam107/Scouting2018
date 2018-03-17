package com.example.vande.scouting2018;

import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.support.design.widget.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.vande.scouting2018.AutonActivity.AUTON_STRING_EXTRA;


public class TeleopActivity extends ScoutingActivity {
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

    @BindView(R.id.fouls_chkbx)
    public CheckBox foulsChbx;

    int teleopCubesInExchange = 0;
    int teleopCubesInOurSwitch = 0;
    int teleopCubesInTheirSwitch = 0;
    int teleopCubesInScale = 0;
    public String auton;

    @Override
    public void initialize() {
        setContentView(R.layout.activity_teleop);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        auton = bundle.getString(AUTON_STRING_EXTRA);

        saveInitials = true;
        previousActivityData = auton;

        addItem(R.id.teleopCubesInExchange_input_layout, teleopCubesInExchangeInputLayout);
        addItem(R.id.teleopCubesInExchange_input, teleopCubesInExchangeInput);
        addItem(R.id.teleopCubesInOurSwitch_input_layout, teleopCubesInOurSwitchInputLayout);
        addItem(R.id.teleopCubesInOurSwitch_input, teleopCubesInOurSwitchInput);
        addItem(R.id.teleopCubesInTheirSwitch_input_layout, teleopCubesInTheirSwitchInputLayout);
        addItem(R.id.teleopCubesInTheirSwitch_input, teleopCubesInTheirSwitchInput);
        addItem(R.id.teleopCubesInScale_input_layout, teleopCubesInScaleInputLayout);
        addItem(R.id.teleopCubesInScale_input, teleopCubesInScaleInput);

        addCheckBoxes("CubePickup", cubePickupFloorCheckBox, cubePickupPortalCheckBox);

        addItem(R.id.climb_RadiobtnGrp, climbRadiobtnGrp);
        addItem(R.id.abilityToHelpClimb_RadiobtnGrp, abilityToHelpClimbRadiobtnGrp);
        addItem(R.id.onPlatform_RadiobtnGrp, onPlatformRadiobtnGrp);
        addItem(R.id.defense_RadiobtnGrp, defenseRadiobtnGrp);

        addCheckBoxes("Fouled", foulsChbx);

        displayTeleopCubesInExchangeInput(teleopCubesInExchange);
        displayTeleopCubesInOurSwitchInput(teleopCubesInOurSwitch);
        displayTeleopCubesInTheirSwitchInput(teleopCubesInTheirSwitch);
        displayTeleopCubesInScaleInput(teleopCubesInScale);
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
}
