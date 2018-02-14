package com.example.vande.scouting2018;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {

                }
            }
        }
        return false;
    }

    public void showMatch(View view) {
        startActivity(new Intent(this, ScouterInitialsActivity.class));
    }

    public void showPit(View view) {
        startActivity(new Intent(this, PitActivity.class));
    }

    public void sendData(View view) {
        startActivity(new Intent(this, SendDataActivity.class));
    }
}
