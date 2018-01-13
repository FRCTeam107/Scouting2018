package utils;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

public class ViewUtils {
    public static boolean requestFocus(View v, Activity activity){
        if(v.requestFocus()){
            activity.getWindow().setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE));
            return true;
        }
        return false;
    }
}