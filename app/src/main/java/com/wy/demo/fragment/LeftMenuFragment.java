package com.wy.demo.fragment;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.demo.R;
import com.wy.demo.activity.AliPayHomeActivity;
import com.wy.demo.activity.ClockActivity;
import com.wy.demo.activity.Code2Activity;
import com.wy.demo.activity.CustomerViewActivity;
import com.wy.demo.activity.DialogActivity;
import com.wy.demo.activity.QQMsgActivity;
import com.wy.demo.activity.ScrollingActivity;
import com.wy.demo.activity.SelectionPhoneActivity;
import com.wy.demo.activity.SliderBarActivity;
import com.wy.demo.activity.SnowActivity;
import com.wy.demo.service.InteractionService;
import com.wy.demo.service.UnInteractionService;
import com.wy.demo.service.WeatherService;
import com.wy.demo.uitls.LocationUtils;

import java.lang.reflect.Method;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Administrator on 2018/12/25.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_view, tv_clock, tv_dialog, tv_contact;
    private TextView tv_huawei;
    private SeekBar seekBar;
    private TextView tv_start_service, tv_stop_service, tv_bind_service, tv_unbind_service;
    private TextView tv_fore_service;
    private Intent unInteractionSIntent, interactionSIntent;
    private TextView tv_location;
    private TextView tv_alipay_home;
    private TextView tv_qqmsg;
    private StringBuffer sb = new StringBuffer();
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
        tv_start_service = view.findViewById(R.id.tv_start_service);
        tv_stop_service = view.findViewById(R.id.tv_stop_service);
        tv_bind_service = view.findViewById(R.id.tv_bind_service);
        tv_unbind_service = view.findViewById(R.id.tv_unbind_service);
        tv_fore_service = view.findViewById(R.id.tv_fore_service);
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
        tv_start_service.setOnClickListener(this);
        tv_stop_service.setOnClickListener(this);
        tv_bind_service.setOnClickListener(this);
        tv_unbind_service.setOnClickListener(this);
        tv_fore_service.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_alipay_home.setOnClickListener(this);
        tv_qqmsg.setOnClickListener(this);
        tv_toolbar.setOnClickListener(this);
        tv_code2.setOnClickListener(this);
        tv_sd.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("main", "progress: " + progress);
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
        switch (view.getId()) {
            case R.id.tv_view:
                startMyActivity(CustomerViewActivity.class);
                break;
            case R.id.tv_snow:
                startMyActivity(SnowActivity.class);
                break;
            case R.id.tv_clock:
                startMyActivity(ClockActivity.class);
                break;
            case R.id.tv_dialog:
                startMyActivity(DialogActivity.class);
                break;
            case R.id.tv_contact:
                startMyActivity(SelectionPhoneActivity.class);
                break;
            case R.id.tv_huawei:
                if (hasNotchInScreen(getContext())) {
                    int[] notchSize = getNotchSize(getContext());
                    Toast.makeText(getActivity(), "刘海屏", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "刘海宽度 " + notchSize[0] + "\t" + "刘海高度 " + notchSize[1], Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "非刘海屏", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_start_service:
                unInteractionSIntent = new Intent(getActivity(), UnInteractionService.class);
                getActivity().startService(unInteractionSIntent);
                break;
            case R.id.tv_stop_service:
                getActivity().stopService(unInteractionSIntent);
                break;
            case R.id.tv_bind_service:
                interactionSIntent = new Intent(getActivity(), InteractionService.class);
                getActivity().bindService(interactionSIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.tv_unbind_service:
                getActivity().unbindService(connection);
                break;
            case R.id.tv_fore_service:
                getActivity().startService(new Intent(getActivity(), WeatherService.class));
                break;
            case R.id.tv_location:
                LocationUtils.initLocation(getActivity());
                Toast.makeText(getActivity(),LocationUtils.latitude+"\t"+LocationUtils.longitude,Toast.LENGTH_SHORT).show();
                Log.e("main", LocationUtils.latitude + "\t" + LocationUtils.longitude);
                break;
            case R.id.tv_alipay_home:
                startMyActivity(AliPayHomeActivity.class);
                break;
            case R.id.tv_qqmsg:
                startMyActivity(QQMsgActivity.class);
                break;
            case R.id.tv_toolbar:
                startMyActivity(ScrollingActivity.class);
                break;
            case R.id.tv_code2:
                startMyActivity(Code2Activity.class);
                break;
            case R.id.tv_sd:
                startMyActivity(SliderBarActivity.class);
                break;
        }
    }

    private void startMyActivity(Class cl) {
        Intent intent = new Intent(getActivity(), cl);
        startActivity(intent);
    }


    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            InteractionService.MyBinder binder = (InteractionService.MyBinder) service;
            binder.showToast();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


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
