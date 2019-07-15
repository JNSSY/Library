package com.wy.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 不可交互的后台服务,
 * 继承service 实现onBind()方法，Service的生命周期很简单，
 * 分别为onCreate、onStartCommand、onDestroy这三个。
 * 当我们startService()的时候，首次创建Service会回调onCreate()方法，
 * 然后回调onStartCommand()方法，再次startService()的时候，
 * 就只会执行一次onStartCommand()。服务一旦开启后，
 * 我们就需要通过stopService()方法或者stopSelf()方法，就能把服务关闭，这时就会回调onDestroy()
 */

public class UnInteractionService extends Service {

    private static final String TAG = "service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "on create");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "on start command");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Log.e(TAG, "执行耗时操作");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "on destroy");
        super.onDestroy();
    }
}
