package com.wy.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wy.demo.R;

@SuppressLint("all")
public class QueryCode extends View {

    private String text;
    private int textColor;
    private float textSize;

    private Paint mPaint;
    private Rect mRect;

    public QueryCode(Context context) {
        this(context, null);
    }

    public QueryCode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QueryCode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.queryCode);
        text = array.getString(R.styleable.queryCode_text);
        textColor = array.getColor(R.styleable.queryCode_textColor, getResources().getColor(R.color.colorPrimaryDark));
        textSize = array.getDimension(R.styleable.queryCode_textSize, getResources().getDimension(R.dimen.smaller));
        array.recycle();


        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);

        mRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mRect);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        //getMeasuredWidth() 返回view 的宽度，在xml文件中给定的宽度，以像素为单位
        //getMeasuredHeight() 返回view 的高度，在xml文件中给定的高度，以像素为单位
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);


        mPaint.setColor(textColor);
        canvas.drawText(text, getWidth() / 2-mRect.width()/2, getHeight() /2+mRect.height()/2, mPaint);

    }
}
