package com.wy.demo.uitls;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 * Created by wangyue on 2019/2/27.
 */
@SuppressWarnings("all")
@RequiresApi(api = Build.VERSION_CODES.M)

public class PermissionUtils {

    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private int permissionIndex = 0;

    private void init(Activity activity) {
        if (Build.VERSION.SDK_INT > 23) {
            checkPermission(activity);
        }
    }


    private void checkPermission(Activity activity) {
        if (permissionIndex < PERMISSIONS.length) {
            int checkSelfPermission = activity.checkSelfPermission(PERMISSIONS[permissionIndex]);
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{PERMISSIONS[permissionIndex]}, 1001);
            } else {
                Toast.makeText(activity, "权限都已申请到！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
