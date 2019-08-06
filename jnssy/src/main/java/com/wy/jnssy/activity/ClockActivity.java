package com.wy.jnssy.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextClock;

import com.wy.jnssy.R;


/**
 * 数字时钟
 * Created by wy on 2018/8/21.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ClockActivity extends BaseActivity {

    private TextClock tc_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        tc_time =  findViewById(R.id.tc_time);
        tc_time.setFormat24Hour("HH:mm:ss");

    }
}
