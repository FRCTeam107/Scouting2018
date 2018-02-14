package com.example.vande.scouting2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.StringUtils;
import utils.ViewUtils;

public class ScouterInitialsActivity extends ScoutingActivity implements View.OnKeyListener {

    @BindView(R.id.scouterInitials_input_layout)
    public TextInputLayout scouterInitialsInputLayout;
    private static String initials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scouter_initials);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {
                    case R.id.scouterInitials_input_layout:
                        scouterInitialsInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }

    public static String getInitials() {
        return initials;
    }

    public void submitInitials(View view) {
        initials = getTextInputLayoutString(scouterInitialsInputLayout);

        if(!StringUtils.isEmptyOrNull(initials))
            startActivity(new Intent(this, AutonActivity.class));
        else
            scouterInitialsInputLayout.setError(getText(R.string.scouterInitialsError));
    }
}