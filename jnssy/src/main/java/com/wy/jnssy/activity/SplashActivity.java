package com.wy.jnssy.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by wangyue on 2019/2/26.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressWarnings("all")

public class SplashActivity extends AppCompatActivity {
    public static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private int permissionIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 23) {
            checkPermission();
        }
    }

    private void checkPermission() {
        if (permissionIndex < PERMISSIONS.length) {
            int checkSelfPermission = checkSelfPermission(PERMISSIONS[permissionIndex]);
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERMISSIONS[permissionIndex]}, 1001);
            } else {
//                permissionIndex++;
//                checkPermission();
                Toast.makeText(this, "权限都已申请到！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            int grantResult = grantResults[0];
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                permissionIndex++;
                checkPermission();
            } else {
                Toast.makeText(this, "请开启权限，否则可能无法使用功能", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
