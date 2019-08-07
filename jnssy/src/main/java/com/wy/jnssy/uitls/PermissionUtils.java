package com.wy.jnssy.uitls;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wy on 2019/2/27.
 */
@RequiresApi(api = Build.VERSION_CODES.M)

public class PermissionUtils {

    private String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
    };

    private int[] perCode = {
            0x1000, 0x1001, 0x1002, 0x1003, 0x1004, 0x1005
    };

    private Activity activity;

    public void init(Activity activity) {
        this.activity = activity;
    }


    public void requestPermission(int index) {
        if (index < PERMISSIONS.length) {
            int checkSelfPermission = activity.checkSelfPermission(PERMISSIONS[index]);
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{PERMISSIONS[index]}, perCode[index]);
            }
        }
    }


}
