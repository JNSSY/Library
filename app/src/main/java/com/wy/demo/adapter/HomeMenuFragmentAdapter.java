package com.wy.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.demo.R;
import com.wy.demo.myinterface.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeMenuFragmentAdapter extends RecyclerView.Adapter<HomeMenuFragmentAdapter.MyViewHolder> {

    private OnClickListener listener;
    private List<String> data;

    public HomeMenuFragmentAdapter() {
        data = new ArrayList<>();
    }

    public void setOnItemClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void addData(String s) {
        data.add(s);
        notifyDataSetChanged();
    }

    public String getData(int position) {
        if (data != null && data.size() > 0) {
            return data.get(position);
        } else {
            return null;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_meun_fragment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String str = data.get(position);

        if (str.equals("chat")) {
            holder.tv_menu.setText("蓝牙通讯");
            holder.iv_view.setImageResource(R.mipmap.chat);
        }
        if (str.equals("huawei_voice")) {
            holder.tv_menu.setText("语音识别");
            holder.iv_view.setImageResource(R.mipmap.voice);
        }
        if (str.equals("music")){
            holder.tv_menu.setText("网易云音乐");
            holder.iv_view.setImageResource(R.mipmap.music);
        }
        if (str.equals("customer_view")){
            holder.tv_menu.setText("自定义控件");
            holder.iv_view.setImageResource(R.mipmap.paint);
        }
        if (str.equals("record_voice")){
            holder.tv_menu.setText("通话录音");
            holder.iv_view.setImageResource(R.mipmap.icon_record);
        }
        if (str.equals("下拉菜单")){
            holder.tv_menu.setText("下拉菜单");
            holder.iv_view.setImageResource(R.mipmap.icon_dropdown_menu);
        }
        if (str.equals("query_code")){
            holder.tv_menu.setText("验证码");
            holder.iv_view.setImageResource(R.mipmap.icon_query_code);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_menu;
        private ImageView iv_view;
        private LinearLayout ll_item;

        public MyViewHolder(View view) {
            super(view);
            tv_menu = view.findViewById(R.id.tv_menu);
            iv_view = view.findViewById(R.id.iv_view);
            ll_item = view.findViewById(R.id.ll_item);

            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getPosition());
                }
            });
        }
    }
}
