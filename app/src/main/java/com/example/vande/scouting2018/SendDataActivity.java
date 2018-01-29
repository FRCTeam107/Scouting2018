package com.example.vande.scouting2018;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import utils.FormatStringUtils;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Matt on 10/9/2017.
 */

public class SendDataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
    }


    public void sendMatchData(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        String file = "storage/emulated/0/Scouting/Match.csv";
        intent.setType("text/plain");
        intent.setPackage("com.android.bluetooth");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(file)));
        startActivity(Intent.createChooser(intent, "Share app"));
    }

    public void sendPitData(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        String file = "storage/emulated/0/Scouting/Pit.csv";
        intent.setType("text/plain");
        intent.setPackage("com.android.bluetooth");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(file)));
        startActivity(Intent.createChooser(intent, "Share app"));
    }

}