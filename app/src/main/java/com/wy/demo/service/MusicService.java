package com.wy.demo.service;

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

import com.wy.demo.Constant;
import com.wy.demo.R;
import com.wy.demo.activity.WangYiMusicActivity;
import com.wy.demo.myinterface.IService;

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
        descriptor = getApplication().getResources().openRawResourceFd(R.raw.zhifou);
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
            mediaPlayer.setDataSource(descriptor);
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
