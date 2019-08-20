package com.wy.jnssy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wy.jnssy.R;
import com.wy.jnssy.uitls.LoadingCircle;
import com.wy.jnssy.uitls.LoadingNormal;

public class LoadingActivity extends BaseActivity {

    private LoadingCircle loadingCircle;
    private LoadingNormal loadingNormal;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        context = this;

        loadingCircle = new LoadingCircle(this, true);
        loadingNormal = new LoadingNormal(context, true);

        findViewById(R.id.bt_show_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingCircle.showLoading("正在加载中，请稍后...");
            }
        });

        findViewById(R.id.bt_progress_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingNormal.showProgress("正在加载中，请稍后...");
            }
        });

        findViewById(R.id.bt_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt("q");
            }
        });



    }

}
