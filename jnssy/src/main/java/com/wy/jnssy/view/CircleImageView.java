package com.wy.jnssy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wy.jnssy.R;
import com.wy.jnssy.uitls.CustomerViewUtils;

@SuppressLint("DrawAllocation")
public class CircleImageView extends View {

    private Paint circlePaint, numPaint;
    private Paint xLine, yLine;
    private String num = "-1";
    private int cirlce_bg_color, num_color;
    private CustomerViewUtils utils;

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


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        utils = new CustomerViewUtils(getContext());

        circlePaint = new Paint();
        numPaint = new Paint();

        xLine = new Paint();
        yLine = new Paint();
        xLine.setStyle(Paint.Style.FILL);
        yLine.setStyle(Paint.Style.FILL);
        xLine.setColor(getResources().getColor(R.color.white));
        yLine.setColor(getResources().getColor(R.color.white));

        circlePaint.setStyle(Paint.Style.FILL);
        numPaint.setStyle(Paint.Style.FILL);


        circlePaint.setColor(cirlce_bg_color);
        numPaint.setColor(num_color);

        numPaint.setTextSize(utils.sp2px(14));
        numPaint.setTextAlign(Paint.Align.CENTER);

        Log.e("wy", getWidth() + " \t" + getHeight());

        //getHeight()获取view的宽度，水平方向 px
        //getHeight()获取view的高度，竖直方向 px
        float x = (float) getWidth() / 2;//圆心x坐标
        float y = (float) getHeight() / 2;//圆心y坐标


        float radius = 30;
        canvas.drawCircle(x, y, radius, circlePaint);


//        canvas.drawLine(x - radius, y, x + radius, y, xLine);
//        canvas.drawLine(x, y - radius, x, y + radius, yLine);

        Paint.FontMetrics metrics = numPaint.getFontMetrics();
        float textHeight = (metrics.ascent + metrics.descent) / 2;

        canvas.drawText(num, x, y - textHeight, numPaint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
