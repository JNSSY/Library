package com.wy.jnssy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.wy.jnssy.R;
import com.wy.jnssy.code2.activity.CaptureActivity;
import com.wy.jnssy.code2.common.BitmapUtils;

public class Code2Activity extends BaseActivity implements View.OnClickListener {

    private Button mBtn1;
    private EditText mEt;
    private Button mBtn2;
    private ImageView mImage;
    private final static int REQ_CODE = 1028;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code2);

        initView();
    }

    private void initView() {
        mBtn1 = findViewById(R.id.btn1);

        mBtn1.setOnClickListener(this);
        mEt = findViewById(R.id.et);
        mBtn2 = findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mImage = findViewById(R.id.image);
        mImage.setOnClickListener(this);
        mTvResult = findViewById(R.id.tv_result);
        mTvResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn1) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, REQ_CODE);
        } else if (i == R.id.btn2) {
            mImage.setVisibility(View.VISIBLE);
            //隐藏扫码结果view
            mTvResult.setVisibility(View.GONE);

            String content = mEt.getText().toString().trim();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtils.create2DCode(content);//根据内容生成二维码
                mTvResult.setVisibility(View.GONE);
                mImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            mImage.setVisibility(View.GONE);
            mTvResult.setVisibility(View.VISIBLE);
            String result;
            if (data != null) {
                result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                if (result != null) {
                    mTvResult.setText("扫码结果：" + result);
                } else {
                    mTvResult.setText("扫码结果：null");
                }
            } else {
                mTvResult.setText("扫码结果：null");
            }

        }


    }

}
