package com.wy.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.wy.demo.R;
import com.wy.demo.code2.activity.CaptureActivity;
import com.wy.demo.code2.common.BitmapUtils;

public class Code2Activity extends AppCompatActivity implements View.OnClickListener {

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
        mBtn1 = (Button) findViewById(R.id.btn1);

        mBtn1.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.et);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(this);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.btn2:
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
                break;
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

    private void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }
}
