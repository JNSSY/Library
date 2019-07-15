package com.wy.demo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wy.demo.R;
import com.wy.demo.activity.MySurfaceActivity;

public class CartFragment extends Fragment {
    private Button bt_sv;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        bt_sv = view.findViewById(R.id.bt_sv);
        bt_sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MySurfaceActivity.class));
            }
        });
        return view;
    }
}
