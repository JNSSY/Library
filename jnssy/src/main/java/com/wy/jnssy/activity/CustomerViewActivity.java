package com.wy.jnssy.activity;

import android.os.Bundle;

import com.wy.jnssy.R;
import com.wy.jnssy.view.CircleBarView;


/**
 * Created by wy on 2018/8/21.
 */

public class CustomerViewActivity extends BaseActivity {

    private CircleBarView cbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);

        cbv = findViewById(R.id.cbv);
        cbv.setProgressNum(100, 3000);
    }

}
