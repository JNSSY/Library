package com.wy.jnssy.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class RecorderService extends Service {

    private MediaRecorder recorder; //录音的一个实例
    private String recordPath;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        recordPath = Environment.getExternalStorageDirectory() + File.separator + "recorder";
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(new MyListener(), PhoneStateListener.LISTEN_CALL_STATE);
        createRecorderFile();
        initRecord();
    }

    class MyListener extends PhoneStateListener {

        //在电话状态改变的时候调用
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:  //空闲状态
                    if (recorder != null) {
                        try {
                            recorder.stop();
                            recorder.reset();
                            recorder.release();
                            Log.e("wy","录音结束");
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态  需要在响铃状态的时候初始化录音服务
                    Log.e("wy", "来电号码：" + incomingNumber);
//                    if (recorder != null) {
//                        recorder.start(); //开始录音
//                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://通话状态
                    if (recorder != null) {
                        recorder.start(); //开始录音
                        Log.e("wy","开始录音");
                    }
                    break;
            }
        }
    }

    //创建保存录音的目录
    private void createRecorderFile() {
        File file = new File(recordPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    //获取当前时间，以其为名来保存录音
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = format.format(date);
        return str;
    }


    private void initRecord() {
        recorder = new MediaRecorder();//初始化录音对象
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);//设置录音的输入源
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);//设置音频格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频编码
//        recorder.setOutputFile(recordPath + "/" + getCurrentTime() + ".mp3"); //设置录音保存的文件
        recorder.setOutputFile(recordPath + "/" +"1" + ".mp3"); //设置录音保存的文件
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}