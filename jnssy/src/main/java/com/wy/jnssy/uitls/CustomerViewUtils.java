package com.wy.jnssy.uitls;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class CustomerViewUtils {

    private Context context;

    public CustomerViewUtils(Context context) {
        this.context = context;
    }

    public float dp2px(float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public float sp2px(float sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }
}
