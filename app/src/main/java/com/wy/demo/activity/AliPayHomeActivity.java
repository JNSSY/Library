package com.wy.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wy.demo.R;


/**
 * Created by Administrator on 2018/12/12.
 */

public class AliPayHomeActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private AppBarLayout mAppBarLayout;
    private View mToolbarOpenBgView;
    private View mToolbarCloseBgView;
    private View mToolbarOpenLayout;
    private View mToolbarCloseLayout;
    private View contentBgView;

    private LinearLayout ll_saoys, ll_pay, ll_shouqian, ll_kabao;
    private ImageView close_scan_iv, close_pay_iv, close_charge_iv, close_card_iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alipay_home);

        initView();

        mAppBarLayout.addOnOffsetChangedListener(this);

    }

    private void initView() {
        mToolbarOpenBgView = findViewById(R.id.toolbar_open_bg_view);
        mToolbarOpenLayout = findViewById(R.id.include_toolbar_open);

        mToolbarCloseBgView = findViewById(R.id.bg_toolbar_close);
        mToolbarCloseLayout = findViewById(R.id.include_toolbar_close);
        contentBgView = findViewById(R.id.content_bg_view);

        mAppBarLayout = findViewById(R.id.app_bar_layout);

        ll_saoys = findViewById(R.id.ll_saoys);
        ll_pay = findViewById(R.id.ll_pay);
        ll_shouqian = findViewById(R.id.ll_shouqian);
        ll_kabao = findViewById(R.id.ll_kabao);

        close_scan_iv = findViewById(R.id.close_scan_iv);
        close_pay_iv = findViewById(R.id.close_pay_iv);
        close_charge_iv = findViewById(R.id.close_charge_iv);
        close_card_iv = findViewById(R.id.close_card_iv);

        ll_saoys.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        ll_shouqian.setOnClickListener(this);
        ll_kabao.setOnClickListener(this);

        close_scan_iv.setOnClickListener(this);
        close_pay_iv.setOnClickListener(this);
        close_charge_iv.setOnClickListener(this);
        close_card_iv.setOnClickListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            mToolbarOpenLayout.setVisibility(View.VISIBLE);
            mToolbarCloseLayout.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            mToolbarOpenBgView.setBackgroundColor(Color.argb(alpha2, 25, 131, 209));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            mToolbarOpenLayout.setVisibility(View.GONE);
            mToolbarCloseLayout.setVisibility(View.VISIBLE);
            float scale3 = (float) (scrollRange - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            mToolbarCloseBgView.setBackgroundColor(Color.argb(alpha3, 25, 131, 209));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        contentBgView.setBackgroundColor(Color.argb(alpha, 25, 131, 209));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_saoys:
                Toast.makeText(this, "扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_pay:
                Toast.makeText(this, "付钱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_shouqian:
                Toast.makeText(this, "收钱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_kabao:
                Toast.makeText(this, "卡包", Toast.LENGTH_SHORT).show();
                break;

            case R.id.close_scan_iv:
                Toast.makeText(this, "扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_pay_iv:
                Toast.makeText(this, "付钱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_charge_iv:
                Toast.makeText(this, "收钱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_card_iv:
                Toast.makeText(this, "卡包", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }

}
