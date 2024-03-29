package com.wy.jnssy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.jnssy.R;
import com.wy.jnssy.bluetoothchat.BluetoothChatService;
import com.wy.jnssy.bluetoothchat.DeviceListActivity;


/**
 * Created by wy on 17-5-26.
 */

public class BluetoothChatActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 0x00;
    private static final int REQUEST_CONNECT_DEVICE = 0x02;
    private TextView mTitle;
    private Button btn_connect;
    private Button btn_discover;
    private BluetoothAdapter mBluetoothAdapter;
    // 成员对象聊天服务
    private BluetoothChatService mChatService = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private ListView mConversationView;
    private Button mSendButton;
    private Button mClearButton;
    private EditText edt;
    // 从BluetoothChatService发送处理程序的消息类型
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String TOAST = "toast";
    public static final String DEVICE_NAME = "device_name";
    private String mConnectedDeviceName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btchat);

        initView();

    }

    private void initView() {

        // 标题栏
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText("open");
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // 连接按钮
        btn_connect = (Button) findViewById(R.id.btn_connect);
        // 发现按钮
        btn_discover = (Button) findViewById(R.id.btn_discover);

        btn_connect.setOnClickListener(this);
        btn_discover.setOnClickListener(this);

        // 获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 判断蓝牙是否可用
        if (mBluetoothAdapter == null) {
            showToast("蓝牙是不可用的");
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_connect) {// 连接设备
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
        } else if (i == R.id.btn_discover) {// 允许被发现设备
            ensureDiscoverable();
        }

    }

    /**
     * 允许设备被搜索
     */
    private void ensureDiscoverable() {

        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                    BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            // 开启蓝牙
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mChatService == null) {
                setupChat();
            }
        }
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
        if (mChatService != null) {
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                mChatService.startChat();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // 判断蓝牙是否启用
                if (resultCode == Activity.RESULT_OK) {
                    // 建立连接
                    setupChat();
                } else {
                    showToast("蓝牙未启用");
                    finish();
                }
                break;

            case REQUEST_CONNECT_DEVICE:
                // 当DeviceListActivity返回与设备连接的消息
                if (resultCode == Activity.RESULT_OK) {
                    // 连接设备的MAC地址
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙对象
                    BluetoothDevice device = mBluetoothAdapter
                            .getRemoteDevice(address);
                    // 开始连接设备
                    mChatService.connect(device);
                }
                break;
        }
    }

    private void setupChat() {

        mConversationArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);
        mSendButton = (Button) findViewById(R.id.button_send);
        mClearButton = (Button) findViewById(R.id.button_clear);
        edt = (EditText) findViewById(R.id.edit_text_out);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = edt.getText().toString();
                sendMessage(message);
                edt.setText("");
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConversationArrayAdapter.clear();
            }
        });

        mChatService = new BluetoothChatService(this, mHandler);

    }

    /**
     * 发送消息
     *
     * @param message
     *            发送的内容
     */
    private void sendMessage(String message) {
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            showToast(getResources().getString(R.string.not_connected));
            return;
        }
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    // 此Handler处理BluetoothChatService传来的消息
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:

                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            mTitle.setText(R.string.devoice_connected_to);
                            mTitle.append(mConnectedDeviceName);
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            mTitle.setText(R.string.devoice_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            mTitle.setText(R.string.devoice_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // 读取到的数据
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  "
                            + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // 保存连接设备的名字
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "连接到" + mConnectedDeviceName, Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止蓝牙
        if (mChatService != null)
            mChatService.stop();
    }




    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
