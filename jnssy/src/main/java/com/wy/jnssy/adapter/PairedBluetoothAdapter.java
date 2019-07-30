package com.wy.jnssy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wy.jnssy.R;
import com.wy.jnssy.entity.Device;

import java.util.List;

public class PairedBluetoothAdapter extends RecyclerView.Adapter<PairedBluetoothAdapter.MyViewHolder> {

    private List<Device> datas;
    private LayoutInflater inflater;

    public PairedBluetoothAdapter(List<Device> datas) {
        if (datas != null && datas.size() > 0) {
            this.datas = datas;
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

        holder.tv_name.setText(datas.get(position).getName());
        holder.tv_addr.setText(datas.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
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
