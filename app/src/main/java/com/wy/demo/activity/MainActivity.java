package com.wy.demo.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.demo.R;
import com.wy.jnssy.entity.Tab;
import com.wy.jnssy.fragment.CartFragment;
import com.wy.jnssy.fragment.CategoryFragment;
import com.wy.jnssy.fragment.HomeFragment;
import com.wy.jnssy.fragment.HotFragment;
import com.wy.jnssy.fragment.MineFragment;
import com.wy.jnssy.uitls.PermissionUtils;
import com.wy.jnssy.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("HandlerLeak")
@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private LayoutInflater mInflater;
    private FragmentTabHost mTabHost;

    private boolean isExit = false;
    private StringBuffer sb = new StringBuffer();
    private List<Tab> tabList = new ArrayList<>(5);
    private PermissionUtils pUtils;
    private int index = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAction();
        initTab();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initPermission();
        }
    }


    private void initPermission() {
        pUtils = new PermissionUtils();
        pUtils.init(this);
        pUtils.requestPermission(index);

    }

    private void initView() {
    }

    private void initAction() {
    }

    private void initTab() {
        Tab tab_home = new Tab(R.string.home, R.drawable.select_icon_home, HomeFragment.class);
        Tab tab_hot = new Tab(R.string.hot, R.drawable.select_icon_hot, HotFragment.class);
        Tab tab_category = new Tab(R.string.category, R.drawable.select_icon_category, CategoryFragment.class);
        Tab tab_cart = new Tab(R.string.cart, R.drawable.select_icon_cart, CartFragment.class);
        Tab tab_mine = new Tab(R.string.mine, R.drawable.select_icon_mine, MineFragment.class);

        tabList.add(tab_home);
        tabList.add(tab_hot);
        tabList.add(tab_category);
        tabList.add(tab_cart);
        tabList.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : tabList) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 1500);
        } else {
            finish();
            System.exit(0);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pUtils.requestPermission(++index);
    }
}
