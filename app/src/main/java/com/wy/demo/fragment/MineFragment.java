package com.wy.demo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wy.demo.R;
import com.wy.demo.activity.UserInfoActivity;
import com.wy.demo.view.WaterMarBg;

import java.io.File;

import static android.app.Activity.RESULT_OK;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MineFragment extends Fragment implements View.OnClickListener {

    private TextView tv_watermark;
    private View view;
    private SimpleDraweeView sdv;
    private RelativeLayout rl_info;
    public static final int EDIT = 0x0001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        findByIdView();
        initView();
        return view;
    }


    private void initView() {
        setWaterMark();
        initAction();
        showIconImage();
    }

    private void findByIdView() {
        tv_watermark = view.findViewById(R.id.tv_watermark);
        sdv = view.findViewById(R.id.sdv);
        rl_info = view.findViewById(R.id.rl_info);
    }

    private void setWaterMark() {
        tv_watermark.setBackground(new WaterMarBg("大司马"));
    }

    private void initAction() {
        rl_info.setOnClickListener(this);
    }

    private void showIconImage() {
        File icon_file = new File(getActivity().getExternalCacheDir() + File.separator + "user_icon");
        if (icon_file.exists()) {
            Uri uri = Uri.parse("file://" + icon_file.getAbsolutePath());
            sdv.setImageURI(uri);
        } else {
            sdv.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_info:
                startActivityForResult(new Intent(getActivity(), UserInfoActivity.class), EDIT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case EDIT:
                    String uri = data.getExtras().getString("path");
                    sdv.setImageURI(uri);
                    break;
            }
        }
    }


}
