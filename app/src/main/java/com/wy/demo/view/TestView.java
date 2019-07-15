package com.wy.demo.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wy.demo.R;

@SuppressLint("DrawAllocation")
public class TestView extends View {

    private Paint paint;
    private Paint paint1,paint2,paint3;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();

        paint.setColor(Color.WHITE);//设置画笔颜色
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔模式为填充
        paint.setStrokeWidth(10f);


        paint1=new Paint();
        paint2=new Paint();
        paint3=new Paint();

        paint1.setStyle(Paint.Style.STROKE);//描边
        paint2.setStyle(Paint.Style.FILL);//填充
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);//描边+填充

        paint1.setStrokeWidth(20);
        paint2.setStrokeWidth(20);
        paint3.setStrokeWidth(20);


    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorPrimaryDark));//设置画布颜色

        canvas.drawPoint(100, 100, paint);
        canvas.drawPoints(new float[]{100, 200,100,300,100,400}, paint);


        canvas.drawLine(100,100,200,200,paint);

        RectF rectF=new RectF(200,400,800,800);
        canvas.drawRoundRect(rectF,30,30,paint);

        canvas.drawCircle(300,950,100,paint1);
        canvas.drawCircle(550,950,100,paint2);
        canvas.drawCircle(800,950,100,paint3);



    }
}
