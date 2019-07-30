package com.wy.jnssy.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.jnssy.R;
import com.wy.jnssy.activity.AliPayHomeActivity;
import com.wy.jnssy.activity.ClockActivity;
import com.wy.jnssy.activity.Code2Activity;
import com.wy.jnssy.activity.CustomerViewActivity;
import com.wy.jnssy.activity.DialogActivity;
import com.wy.jnssy.activity.QQMsgActivity;
import com.wy.jnssy.activity.ScrollingActivity;
import com.wy.jnssy.activity.SelectionPhoneActivity;
import com.wy.jnssy.activity.SliderBarActivity;
import com.wy.jnssy.activity.SnowActivity;
import com.wy.jnssy.uitls.LocationUtils;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/12/25.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_view, tv_clock, tv_dialog, tv_contact;
    private TextView tv_huawei;
    private SeekBar seekBar;
    private TextView tv_location;
    private TextView tv_alipay_home;
    private TextView tv_qqmsg;
    private TextView tv_toolbar;
    private TextView tv_code2;
    private TextView tv_sd;
    private TextView tv_snow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_menu, null);
        initView();
        initAction();
        return view;
    }

    private void initView() {
        tv_view = view.findViewById(R.id.tv_view);
        tv_clock = view.findViewById(R.id.tv_clock);
        tv_snow = view.findViewById(R.id.tv_snow);
        seekBar = view.findViewById(R.id.seekBar);
        tv_dialog = view.findViewById(R.id.tv_dialog);
        tv_contact = view.findViewById(R.id.tv_contact);
        tv_huawei = view.findViewById(R.id.tv_huawei);
        tv_location = view.findViewById(R.id.tv_location);
        tv_alipay_home = view.findViewById(R.id.tv_alipay_home);
        tv_qqmsg = view.findViewById(R.id.tv_qqmsg);
        tv_toolbar = view.findViewById(R.id.tv_toolbar);
        tv_code2 = view.findViewById(R.id.tv_code2);
        tv_sd = view.findViewById(R.id.tv_sd);
    }

    private void initAction() {
        tv_view.setOnClickListener(this);
        tv_clock.setOnClickListener(this);
        tv_snow.setOnClickListener(this);
        tv_dialog.setOnClickListener(this);
        tv_contact.setOnClickListener(this);
        tv_huawei.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_alipay_home.setOnClickListener(this);
        tv_qqmsg.setOnClickListener(this);
        tv_toolbar.setOnClickListener(this);
        tv_code2.setOnClickListener(this);
        tv_sd.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("wy", "progress: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_view) {
            startMyActivity(CustomerViewActivity.class);
        } else if (i == R.id.tv_snow) {
            startMyActivity(SnowActivity.class);
        } else if (i == R.id.tv_clock) {
            startMyActivity(ClockActivity.class);
        } else if (i == R.id.tv_dialog) {
            startMyActivity(DialogActivity.class);
        } else if (i == R.id.tv_contact) {
            startMyActivity(SelectionPhoneActivity.class);
        } else if (i == R.id.tv_huawei) {
            if (hasNotchInScreen(getContext())) {
                int[] notchSize = getNotchSize(getContext());
                Toast.makeText(getActivity(), "刘海屏", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "刘海宽度 " + notchSize[0] + "\t" + "刘海高度 " + notchSize[1], Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "非刘海屏", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.tv_location) {
            LocationUtils.initLocation(getActivity());//GPS定位室内无信号
            Toast.makeText(getActivity(), LocationUtils.latitude + "\t" + LocationUtils.longitude, Toast.LENGTH_SHORT).show();
            Log.e("wy", LocationUtils.latitude + "\t" + LocationUtils.longitude);
        } else if (i == R.id.tv_alipay_home) {
            startMyActivity(AliPayHomeActivity.class);
        } else if (i == R.id.tv_qqmsg) {
            startMyActivity(QQMsgActivity.class);
        } else if (i == R.id.tv_toolbar) {
            startMyActivity(ScrollingActivity.class);
        } else if (i == R.id.tv_code2) {
            startMyActivity(Code2Activity.class);
        } else if (i == R.id.tv_sd) {
            startMyActivity(SliderBarActivity.class);
        }
    }

    private void startMyActivity(Class cl) {
        Intent intent = new Intent(getActivity(), cl);
        startActivity(intent);
    }


    private boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    public static int[] getNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

}
