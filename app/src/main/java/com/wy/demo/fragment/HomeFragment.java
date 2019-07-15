package com.wy.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wy.demo.R;
import com.wy.demo.activity.CustomerViewActivity;
import com.wy.demo.activity.DropDownActivity;
import com.wy.demo.activity.HuaweiVoiceActivity;
import com.wy.demo.activity.OpenBtActivity;
import com.wy.demo.activity.RecordActivity;
import com.wy.demo.activity.WangYiMusicActivity;
import com.wy.demo.adapter.HomeMenuFragmentAdapter;
import com.wy.demo.myinterface.OnClickListener;



public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView rv_menu;
    private HomeMenuFragmentAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        rv_menu = view.findViewById(R.id.rv_menu);
    }

    private void initData() {
        adapter = new HomeMenuFragmentAdapter();
        adapter.addData("chat");
        adapter.addData("huawei_voice");
        adapter.addData("music");
        adapter.addData("customer_view");
        adapter.addData("record_voice");
        adapter.addData("下拉菜单");
        rv_menu.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv_menu.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                String str = adapter.getData(position);
                if (str == null) {
                    return;
                }
                goActivity(str);
            }
        });
    }

    private void goActivity(String str) {
        if (str.equals("chat")){
            Intent intent=new Intent(getActivity(), OpenBtActivity.class);
            startActivity(intent);
        }if (str.equals("huawei_voice")){
            Intent intent=new Intent(getActivity(), HuaweiVoiceActivity.class);
            startActivity(intent);
        }
        if (str.equals("music")){
            Intent intent=new Intent(getActivity(), WangYiMusicActivity.class);
            startActivity(intent);
        }
        if (str.equals("customer_view")){
            Intent intent=new Intent(getActivity(), CustomerViewActivity.class);
            startActivity(intent);
        }
        if (str.equals("record_voice")){
            Intent intent=new Intent(getActivity(), RecordActivity.class);
            startActivity(intent);
        }
        if (str.equals("下拉菜单")){
            Intent intent=new Intent(getActivity(), DropDownActivity.class);
            startActivity(intent);
        }
    }









}
