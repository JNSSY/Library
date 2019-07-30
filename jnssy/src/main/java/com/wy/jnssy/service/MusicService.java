package com.wy.jnssy.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.wy.jnssy.Constant;
import com.wy.jnssy.activity.WangYiMusicActivity;
import com.wy.jnssy.myinterface.IService;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor descriptor;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        try {
            descriptor = getAssets().openFd("zhifou.mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void playMusic() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
//        updateSeekBar();
    }

    private void updateSeekBar() {
        final int duration = mediaPlayer.getDuration();

        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int currentPosition = mediaPlayer.getCurrentPosition();
                Message msg = Message.obtain();
                msg.what = Constant.UPDATE_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                WangYiMusicActivity.handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 100, 1000);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer.cancel();
                timerTask.cancel();
            }
        });
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void rePlayMusic() {
        mediaPlayer.start();
    }


    private class MyBinder extends Binder implements IService {

        @Override
        public void playMusic() {
            MusicService.this.playMusic();
        }

        @Override
        public void pauseMusic() {
            MusicService.this.pauseMusic();
        }

        @Override
        public void rePlayMusic() {
            MusicService.this.rePlayMusic();
        }

        @Override
        public void seekTo(int position) {
            mediaPlayer.seekTo(position);
        }
    }
}
