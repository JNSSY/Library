package com.wy.demo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewConfiguration;


import com.wy.demo.R;
import com.wy.demo.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wy on 2018/12/11.
 */
@SuppressWarnings("all")
public class WeatherService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();

        Log.e("main", String.valueOf(ViewConfiguration.get(this).getScaledTouchSlop()));
    }

    private void showNotification() {
        Notification.Builder notify = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getDate(System.currentTimeMillis()))
                .setContentText("今日大雪");

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notify.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = notify.build();
        nm.notify(0,notification);
        startForeground(0,notification);
    }

    private String getDate(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
        return dateFormat.format(new Date(time));
    }
}
