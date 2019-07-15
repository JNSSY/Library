package com.wy.demo.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wy.demo.R;
import com.wy.demo.entity.Device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE;
import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothAdapter.EXTRA_STATE;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_ON;

public class OpenBtActivity extends AppCompatActivity implements View.OnClickListener {

    public static BluetoothAdapter btAdapter;
    private int REQUEST_OPEN_BT = 0x0000;
    private int REQUEST_ENABLE_BT = 0x0001;
    private Button bt_open, bt_btchat;

    private List<Device> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_bt);

        initView();
        initBTAdapter();
        getBluetoothStates();
        getPairedDevices();
    }

    private void initView() {
        bt_open = findViewById(R.id.bt_open);
        bt_btchat = findViewById(R.id.bt_btchat);

        bt_open.setOnClickListener(this);
        bt_btchat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_open:
                openBluetooth();
                break;
            case R.id.bt_btchat:
                Intent intent = new Intent(this, BluetoothChatActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getBluetoothStates() {
        IntentFilter filter = new IntentFilter(ACTION_STATE_CHANGED);
        registerReceiver(btStatesReceiver, filter);
    }


    private void initBTAdapter() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            showToast("当前设备暂不支持蓝牙");
            bt_open.setClickable(false);
        } else {
            bt_open.setClickable(true);
        }
    }

    private void openBluetooth() {
        if (btAdapter.isEnabled()) {
            goBluetoothActivity();
        } else {
            Intent intent = new Intent(ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
    }

    private void goBluetoothActivity() {
        Intent intent = new Intent(this, BluetoothActivity.class);
        intent.putExtra("datas", (Serializable) datas);
        startActivityForResult(intent, REQUEST_OPEN_BT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                showToast("蓝牙不可用");
            } else {
                showToast("蓝牙已可用");
                goBluetoothActivity();
            }
        }
        if (requestCode == REQUEST_OPEN_BT && resultCode == RESULT_OK) {
            goBluetoothActivity();
        }
    }


    private BroadcastReceiver btStatesReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_STATE_CHANGED)) {
                int states = intent.getIntExtra(EXTRA_STATE, -1);
                switch (states) {
                    case STATE_TURNING_ON:
//                        showToast("正在打开蓝牙...");
                        break;
                    case STATE_ON:
                        showToast("蓝牙开启成功");
                        break;
                    case STATE_TURNING_OFF:
//                        showToast("正在关闭蓝牙...");
                        break;
                    case STATE_OFF:
                        showToast("蓝牙已关闭");
                        break;
                    case -1:
                        showToast("蓝牙状态异常，请重启设备");
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        if (btStatesReceiver != null) {
           this.unregisterReceiver(btStatesReceiver);
        }
        super.onDestroy();
    }

    private void getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Device d = new Device(device.getName(), device.getAddress());
                datas.add(d);
            }
        }
    }
}
