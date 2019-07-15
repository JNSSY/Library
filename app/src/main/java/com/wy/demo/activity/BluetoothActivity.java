package com.wy.demo.activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wy.demo.R;
import com.wy.demo.adapter.PairedBluetoothAdapter;
import com.wy.demo.adapter.SearchBluetoothAdapter;
import com.wy.demo.entity.Device;
import com.wy.demo.fragment.HomeFragment;
import com.wy.demo.widget.Decoration;

import java.util.ArrayList;


public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_search,tv_app_info;
    private RecyclerView rv_paired, rv_enable;
    private PairedBluetoothAdapter pairedAdapter;
    private SearchBluetoothAdapter searchAdapter;
    private ArrayList<Device> pairedData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        initView();
        initData();
        initAdapter();

    }

    private void initData() {
        ArrayList<Device> data = (ArrayList<Device>) getIntent().getSerializableExtra("datas");
        pairedData.addAll(data);
    }

    private void initAdapter() {
        searchAdapter = new SearchBluetoothAdapter();
        rv_enable.addItemDecoration(new Decoration(getResources().getColor(R.color.div_line), 1, 45, 45));
        rv_enable.setLayoutManager(new LinearLayoutManager(this));
        rv_enable.setAdapter(searchAdapter);

        pairedAdapter = new PairedBluetoothAdapter(pairedData);
        rv_paired.setLayoutManager(new LinearLayoutManager(this));
        rv_paired.addItemDecoration(new Decoration(getResources().getColor(R.color.div_line), 1, 45, 45));
        rv_paired.setAdapter(pairedAdapter);
    }

    private void initView() {
        tv_search = findViewById(R.id.tv_search);
        tv_app_info = findViewById(R.id.tv_app_info);
        rv_paired = findViewById(R.id.rv_paired);
        rv_enable = findViewById(R.id.rv_enable);


        tv_app_info.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_app_info:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
                break;
            case R.id.tv_search:
                searchEnableBluetooth();
                break;
        }
    }

    private void searchEnableBluetooth() {
        if (OpenBtActivity.btAdapter.startDiscovery()) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(searchReceiver, filter);
        } else {
            Log.e("main", "%%%%");
        }
    }


    private BroadcastReceiver searchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                searchAdapter.addData(device);
                searchAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchReceiver != null) {
            unregisterReceiver(searchReceiver);
        }
    }
}
