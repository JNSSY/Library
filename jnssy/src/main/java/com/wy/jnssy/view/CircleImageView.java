package com.wy.jnssy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.wy.jnssy.R;

@SuppressLint("DrawAllocation")
public class CircleImageView extends View {

    private Paint circlePaint, numPaint;
    private String num = "-1";
    private int cirlce_bg_color, num_color;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        num = array.getString(R.styleable.CircleImageView_num);
        cirlce_bg_color = array.getColor(R.styleable.CircleImageView_bg_circle, getResources().getColor(R.color.colorPrimaryDark));
        num_color = array.getColor(R.styleable.CircleImageView_num_color, getResources().getColor(R.color.white));
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint = new Paint();
        numPaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        numPaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(cirlce_bg_color);
        numPaint.setColor(num_color);

        numPaint.setTextSize(dp2px(14));
        numPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics metrics = numPaint.getFontMetrics();

        float centerY = (float) getHeight() / 2;
        float centerX = (float) getWidth() / 2;
        float textHeight = (metrics.ascent + metrics.descent) / 2;


        canvas.drawCircle((float) getWidth() / 2, (float) getHeight() / 2, dp2px(9), circlePaint);
        canvas.drawText(num, centerX, centerY - textHeight, numPaint);

    }

    private float dp2px(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }


}
