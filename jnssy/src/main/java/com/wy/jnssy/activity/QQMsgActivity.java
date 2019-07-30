package com.wy.jnssy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wy.jnssy.R;
import com.wy.jnssy.view.QQMsgView;


/**
 * Created by wy on 2018/12/12.
 */

public class QQMsgActivity extends AppCompatActivity {

    private Button resetBtn, msgCountBtn;
    private QQMsgView qqMsgView;
    private EditText msgCountEt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqmsg);

        resetBtn = findViewById(R.id.reset_btn);
        msgCountBtn = findViewById(R.id.msg_count_btn);
        qqMsgView = findViewById(R.id.drag_ball_view);

        msgCountEt = findViewById(R.id.msg_count_et);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqMsgView.reset();
            }
        });

        msgCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(msgCountEt.getText().toString().trim())) {
                    int count = Integer.valueOf(msgCountEt.getText().toString().trim());
                    qqMsgView.setMsgCount(count);
                }
            }
        });

        qqMsgView.setOnDragBallListener(new QQMsgView.OnDragBallListener() {
            @Override
            public void onDisappear() {
                Toast.makeText(QQMsgActivity.this, "消失了", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
