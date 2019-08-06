package com.wy.jnssy.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wy.jnssy.R;
import com.wy.jnssy.service.RecorderService;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends BaseActivity {
    private TextView tv_record, tv_call, tv_play;
    private EditText et_phone_num;
    private String recordPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        tv_record = findViewById(R.id.tv_record);
        tv_call = findViewById(R.id.tv_call);
        et_phone_num = findViewById(R.id.et_phone_num);
        tv_play = findViewById(R.id.tv_play);

        recordPath = Environment.getExternalStorageDirectory() + File.separator + "recorder/";

        tv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecordActivity.this, RecorderService.class);
                startService(i);
            }
        });

        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(et_phone_num.getText().toString());
            }
        });

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecorder();
            }
        });


    }

    private void playRecorder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                try {
//                    Uri uri = Uri.parse("file://" + recordPath + "zhifou.mp3");
//                    mediaPlayer.setDataSource(RecordActivity.this, uri);
                    File file=new File(recordPath+"1.mp3");
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        }).start();
    }

    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + phoneNum);
        intent.setData(uri);
        startActivity(intent);
    }
}
