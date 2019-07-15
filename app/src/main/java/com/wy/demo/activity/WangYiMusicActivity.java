package com.wy.demo.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.wy.demo.Constant;
import com.wy.demo.R;
import com.wy.demo.myinterface.IService;
import com.wy.demo.service.MusicService;

public class WangYiMusicActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_play, bt_pause, bt_replay;
    private IService iService;
    private MyServiceCollection coll;
    private static SeekBar sb_progress;

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.UPDATE_PROGRESS:
                    Bundle data = msg.getData();
                    int duration = data.getInt("duration");
                    int currentPosition = data.getInt("currentPosition");
                    sb_progress.setMax(duration);
                    sb_progress.setProgress(currentPosition);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangyimusic);
        initView();

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        coll = new MyServiceCollection();
        bindService(intent, coll, BIND_AUTO_CREATE);
    }

    private void initView() {
        bt_play = findViewById(R.id.bt_play);
        bt_pause = findViewById(R.id.bt_pause);
        bt_replay = findViewById(R.id.bt_replay);
        sb_progress = findViewById(R.id.sb_progress);

        bt_play.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_replay.setOnClickListener(this);


        sb_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                iService.seekTo(progress);
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                iService.playMusic();
                break;
            case R.id.bt_pause:
                iService.pauseMusic();
                break;
            case R.id.bt_replay:
                iService.rePlayMusic();
                break;
        }
    }


    private class MyServiceCollection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iService = (IService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(coll);
        super.onDestroy();
    }
}
