package com.wy.jnssy.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wy.jnssy.R;

import java.util.Calendar;


/**
 * Created by Administrator on 2018/8/27.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;
    private TextView tv_date, tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_show.setOnClickListener(this);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);

        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_show) {
            showMyDialog(R.layout.login_pw);
        } else if (i == R.id.tv_date) {
            showDateDialog();
        } else if (i == R.id.tv_time) {
            showTimeDialog();
        }
    }

    private void showMyDialog(int login_pw) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View login_view = LayoutInflater.from(this).inflate(login_pw, null);
//        View login_view = getLayoutInflater().inflate(login_pw, null);
        builder.setView(login_view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.show();
        login_view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        login_view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    private void showDateDialog() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.e("wy", year + "\t" + (month + 1) + "\t" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimeDialog() {
        Calendar c = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.e("wy", hourOfDay + "\t" + minute);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

        dialog.show();
    }
}
