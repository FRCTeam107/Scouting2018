package utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.example.vande.scouting2018.BuildConfig;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class BluetoothUtils {
    public static void sendFileWithIntent(Activity activity, String fileName) {
        if(PermissionUtils.getPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            File file = new File(fileName);
            if(file.exists()) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.android.bluetooth");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file));
                activity.startActivity(Intent.createChooser(intent, "Share app"));
            }
        }
    }

    public static void sendFilesWithIntent(Activity activity, String directoryName) {
        File folder = new File(directoryName);
        File[] files = folder.listFiles();

        if(PermissionUtils.getPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if(files != null) {
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/jpeg");
                intent.setPackage("com.android.bluetooth");

                ArrayList<Uri> toSend = new ArrayList<>();

                for (int i = 0; i < files.length; i++) {
                    toSend.add(FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", files[i]));
                }

                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, toSend);
                activity.startActivity(Intent.createChooser(intent, "Share app"));
            }
        }
    }
}
