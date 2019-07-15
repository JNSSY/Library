package com.wy.demo.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wy.demo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wy on 2018/12/21.
 */

public class SearchBluetoothAdapter extends RecyclerView.Adapter<SearchBluetoothAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private List<BluetoothDevice> data;

    public SearchBluetoothAdapter() {
        data = new ArrayList<>();
    }

    public void addData(BluetoothDevice device) {
        if (data != null && device != null) {
            if (device.getName() != null) {
                if (!data.contains(device)) {
                    data.add(device);
                }
            }
        }
    }

    public void clearData() {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itme_bluetooth, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(data.get(position).getName());
        holder.tv_addr.setText(data.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_addr;

        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_addr = view.findViewById(R.id.tv_addr);
        }
    }
}
