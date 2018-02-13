package utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {
    public static boolean getPermissions(Activity activity, String type) {
        int permission = ContextCompat.checkSelfPermission(activity, type);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{type}, 1);
        }
        return permission == PackageManager.PERMISSION_GRANTED;
    }
}
