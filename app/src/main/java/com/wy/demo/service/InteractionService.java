package com.wy.demo.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 可交互的后台服务是指前台页面可以调用后台服务的方法，
 * 可交互的后台服务实现步骤是和不可交互的后台服务实现步骤是一样的，
 * 区别在于启动的方式和获得Service的代理对象
 */

public class InteractionService extends Service {

    private static final String TAG = "service";

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "on bind");
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void showToast() {
            Log.e(TAG, "show toast");
        }
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "on create");
        super.onCreate();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e(TAG, "unbind service");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "on destroy");
        super.onDestroy();
    }
}
