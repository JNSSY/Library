package com.wy.jnssy.uitls;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wy.jnssy.R;

public class LoadingCircle {

    private Activity activity;
    private View loadingView;
    private TextView tv_msg;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private boolean cancelAble;


    public LoadingCircle(Activity activity, boolean cancelAble) {
        this.activity = activity;
        this.cancelAble = cancelAble;
        init();
    }

    private void init() {
        loadingView = LayoutInflater.from(activity).inflate(R.layout.layout_load, null);
        tv_msg = loadingView.findViewById(R.id.tv_msg);
        builder = new AlertDialog.Builder(activity);
        builder.setView(loadingView);
        builder.setCancelable(cancelAble);
    }


    public void showLoading(String msg) {
        if (dialog == null) {
            tv_msg.setText(msg);
            dialog = builder.show();
        } else {
            tv_msg.setText(msg);
            dialog.show();
        }
    }


    public void dismissLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
