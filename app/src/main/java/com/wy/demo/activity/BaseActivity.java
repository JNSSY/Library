package com.wy.demo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
    }

    public void showProgress(String msg) {
        progressDialog.setTitle(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dissmissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
