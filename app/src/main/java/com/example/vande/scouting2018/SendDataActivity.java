package com.example.vande.scouting2018;

import android.Manifest;
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
import android.os.Parcelable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import utils.FormatStringUtils;
import utils.PermissionUtils;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Matt on 10/9/2017.
 */

public class SendDataActivity extends ScoutingActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
    }

    public void sendMatchData(View view) {
        if(checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String file = "storage/emulated/0/Scouting/Match.csv";
            intent.setType("text/plain");
            intent.setPackage("com.android.bluetooth");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(file)));
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }

    public void sendPitData(View view) {
        if(checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String file = "storage/emulated/0/Scouting/Pit.csv";
            intent.setType("text/plain");
            intent.setPackage("com.android.bluetooth");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(file)));
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }

    public void sendRobotPhotos(View view) {
        if(checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/jpeg");
            intent.setPackage("com.android.bluetooth");
            String dir = "storage/emulated/0/Scouting/Photos/";

            File folder = new File(dir);
            File[] photos = folder.listFiles();

            ArrayList<Uri> toSend = new ArrayList<>();

            for (int i = 0; i < photos.length; i++) {
                toSend.add(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photos[i]));
            }

            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, toSend);
            startActivity(Intent.createChooser(intent, "Share app"));
        }
    }
}