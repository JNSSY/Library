package com.wy.demo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hiai.asr.AsrConstants;
import com.huawei.hiai.asr.AsrError;
import com.huawei.hiai.asr.AsrEvent;
import com.huawei.hiai.asr.AsrListener;
import com.huawei.hiai.asr.AsrRecognizer;
import com.wy.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HuaweiVoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int END_AUTO_TEST = 0;
    private final static int INIT_ENGINE = 1;
    private static final int DELAYED_SATRT_RECORD = 4;
    private static final int DELAYED_STOP_RECORD = 5;


    private AsrRecognizer mAsrRecognizer;
    private TextView showResult;
    private Button stopListeningBtn;
    private Button startRecord;

    private boolean isAutoTest = true;
    private boolean isAutoTestEnd = false;
    private boolean isWritePcm = false;
    private int count = 0;

    private MyAsrListener mMyAsrListener = new MyAsrListener();
    private List<String> pathList = new ArrayList<>();

    private String TEST_RESULT_FILE_PATH = "/storage/emulated/0/Android/data/com.huawei.hiai/files/result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huawei_voice);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        makeResDir();
        initView();
        if (isSupportAsr()) {
            initEngine(AsrConstants.ASR_SRC_TYPE_RECORD);
        } else {
            Log.e(TAG, "not support asr!");
        }
    }

    private boolean isSupportAsr() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.huawei.hiai", 0);
            if (packageInfo.versionCode <= 801000300) {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    private void makeResDir() {
        File result = new File(TEST_RESULT_FILE_PATH);
        if (!result.exists()) {
            result.mkdir();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        reset();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyEngine();
        mAsrRecognizer = null;
    }


    private void initView() {
        stopListeningBtn = findViewById(R.id.button_stopListening);
        stopListeningBtn.setOnClickListener(this);

        startRecord = findViewById(R.id.start_record);
        startRecord.setOnClickListener(this);

        showResult = findViewById(R.id.start_record_show);

    }


    private void destroyEngine() {
        if (mAsrRecognizer != null) {
            mAsrRecognizer.destroy();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_stopListening:
                stopListening();
                break;
            case R.id.start_record:
                startRecord();
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case INIT_ENGINE:
                    handleInitEngine();
                    break;
                case DELAYED_SATRT_RECORD:
                    if (isAutoTestEnd || isWritePcm) {
                        if (mAsrRecognizer != null) {
                            mAsrRecognizer.destroy();
                        }
                        mHandler.sendEmptyMessageDelayed(END_AUTO_TEST, 300);
                    } else {
                        startListening(-1, null);
                    }
                    break;
                case DELAYED_STOP_RECORD:

                    break;
                default:
                    break;
            }
        }
    };


    public void setBtEnabled(boolean isEnabled) {
        stopListeningBtn.setEnabled(isEnabled);
        startRecord.setEnabled(isEnabled);
    }


    private void startRecord() {
        isAutoTest = false;
        startRecord.setEnabled(false);
        showResult.setText("识别中：");
        mHandler.sendEmptyMessage(DELAYED_SATRT_RECORD);
    }

    private void initEngine(int srcType) {
        mAsrRecognizer = AsrRecognizer.createAsrRecognizer(this);
        Intent initIntent = new Intent();
        initIntent.putExtra(AsrConstants.ASR_AUDIO_SRC_TYPE, srcType);

        if (mAsrRecognizer != null) {
            mAsrRecognizer.init(initIntent, mMyAsrListener);
        }
    }

    private void handleInitEngine() {
        if (isAutoTest) {
            initEngine(AsrConstants.ASR_SRC_TYPE_FILE);
            setBtEnabled(false);
            startListening(AsrConstants.ASR_SRC_TYPE_FILE, pathList.get(count));
        }
    }

    private void startListening(int srcType, String filePath) {
        Intent intent = new Intent();
        intent.putExtra(AsrConstants.ASR_VAD_FRONT_WAIT_MS, 4000);
        intent.putExtra(AsrConstants.ASR_VAD_END_WAIT_MS, 5000);
        intent.putExtra(AsrConstants.ASR_TIMEOUT_THRESHOLD_MS, 20000);
        if (srcType == AsrConstants.ASR_SRC_TYPE_FILE) {
            intent.putExtra(AsrConstants.ASR_SRC_FILE, filePath);
        }
        if (mAsrRecognizer != null) {
            mAsrRecognizer.startListening(intent);
        }
    }

    private void stopListening() {
        if (mAsrRecognizer != null) {
            mAsrRecognizer.stopListening();
        }
    }


    private class MyAsrListener implements AsrListener {
        @Override
        public void onInit(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            if (error == AsrError.SUCCESS) {
                return;
            }

            if (error == AsrError.ERROR_CLIENT_INSUFFICIENT_PERMISSIONS) {
                Toast.makeText(getApplicationContext(), "请在设置中打开麦克风权限!", Toast.LENGTH_LONG).show();
            }

            setBtEnabled(true);
        }


        @Override
        public void onResults(Bundle results) {
            getOnResult(results, AsrConstants.RESULTS_RECOGNITION);

            stopListening();
            startRecord.setEnabled(true);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults() called with: partialResults = [" + partialResults + "]");
            getOnResult(partialResults, AsrConstants.RESULTS_PARTIAL);
        }

        @Override
        public void onEnd() {

        }

        private String getOnResult(Bundle partialResults, String key) {
            Log.d(TAG, "getOnResult() called with: getOnResult = [" + partialResults + "]");
            String json = partialResults.getString(key);
            final StringBuilder sb = new StringBuilder();
            try {
                JSONObject result = new JSONObject(json);
                JSONArray items = result.getJSONArray("result");
                for (int i = 0; i < items.length(); i++) {
                    String word = items.getJSONObject(i).getString("word");
                    double confidences = items.getJSONObject(i).getDouble("confidence");
                    sb.append(word);
                    Log.d(TAG, "asr_engine: result str " + word);
                    Log.d(TAG, "asr_engine: confidence " + String.valueOf(confidences));
                }
                Log.d(TAG, "getOnResult: " + sb.toString());
                showResult.setText(sb.toString());
            } catch (JSONException exp) {
                Log.w(TAG, "JSONException: " + exp.toString());
            }
            return sb.toString();
        }


        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent() called with: eventType = [" + eventType + "], params = [" + params + "]");
            switch (eventType) {
                case AsrEvent.EVENT_PERMISSION_RESULT:
                    int result = params.getInt(AsrEvent.EVENT_KEY_PERMISSION_RESULT, PackageManager.PERMISSION_DENIED);
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        reset();
                    }
                default:
                    return;
            }
        }

        @Override
        public void onLexiconUpdated(String s, int i) {

        }
    }


    private void reset() {
        setBtEnabled(true);
        showResult.setText("");
    }

}
