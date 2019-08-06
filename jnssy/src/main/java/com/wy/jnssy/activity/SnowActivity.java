package com.wy.jnssy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wy.jnssy.R;
import com.wy.jnssy.view.FallObject;
import com.wy.jnssy.view.FallingView;

public class SnowActivity extends BaseActivity {

    private FallingView fallingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow);

        initView();
    }

    private void initView() {
        FallObject.Builder builder = new FallObject.Builder(getResources().getDrawable(R.drawable.ic_snow));
        FallObject fallObject = builder
                .setSpeed(5, true)
                .setSize(50, 50, true)
                .setWind(5, true, true)
                .build();

        fallingView = findViewById(R.id.fv);
        fallingView.addFallObject(fallObject, 200);//添加200个雪球对象
    }

}
