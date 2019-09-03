package com.wy.jnssy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wy.jnssy.R;
import com.wy.jnssy.activity.CustomerViewActivity;
import com.wy.jnssy.activity.DropDownActivity;
import com.wy.jnssy.activity.LoadingActivity;
import com.wy.jnssy.activity.OpenBtActivity;
import com.wy.jnssy.activity.QueryCodeActivity;
import com.wy.jnssy.activity.RecordActivity;
import com.wy.jnssy.activity.SurfaceViewActivity;
import com.wy.jnssy.activity.WangYiMusicActivity;
import com.wy.jnssy.adapter.HomeMenuFragmentAdapter;
import com.wy.jnssy.myinterface.OnClickListener;
import com.wy.lib.ImageSelectActivity;

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
        adapter.addData("music");
        adapter.addData("customer_view");
        adapter.addData("record_voice");
        adapter.addData("下拉菜单");
        adapter.addData("query_code");
        adapter.addData("loading");
        adapter.addData("多图上传");
        adapter.addData("SVIEW");
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
        if (str.equals("query_code")){
            Intent intent=new Intent(getActivity(), QueryCodeActivity.class);
            startActivity(intent);
        }
        if (str.equals("loading")) {
            Intent intent = new Intent(getActivity(), LoadingActivity.class);
            startActivity(intent);
        }
        if (str.equals("多图上传")) {
            Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
            startActivity(intent);
        }
        if (str.equals("SVIEW")) {
            Intent intent = new Intent(getActivity(), SurfaceViewActivity.class);
            startActivity(intent);
        }
    }


}
