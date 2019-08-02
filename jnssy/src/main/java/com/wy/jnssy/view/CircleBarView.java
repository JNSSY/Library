package com.wy.jnssy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleBarView extends View {

    private Paint progressPaint;//绘制圆弧的画笔
    private Paint bgPaint;//绘制背景圆弧的画笔
    private CircleAnim anim;

    private float progressSweepAngle;//进度条圆弧扫过的角度
    private float startAngle = 120;//背景圆弧的起始角度
    private float sweepAngle = 300;//背景圆弧扫过的角度

    private float progressNum;//可以更新的进度条数值
    private float maxNum = 100;//进度条最大值

    public CircleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        progressPaint.setColor(Color.GREEN);
        progressPaint.setAntiAlias(true);//设置抗锯齿
        progressPaint.setStrokeWidth(15);//随便设置一个画笔宽度，看看效果就好，之后会通过attr自定义属性进行设置

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        bgPaint.setColor(Color.GRAY);
        bgPaint.setAntiAlias(true);//设置抗锯齿
        bgPaint.setStrokeWidth(15);


        anim = new CircleAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(50, 50, 350, 350);//建一个大小为300 * 300的正方形区域
        canvas.drawArc(rectF, startAngle, sweepAngle, false, bgPaint);
        canvas.drawArc(rectF, startAngle, progressSweepAngle, false, progressPaint);
    }


    //设置动画时间
    public void setProgressNum(float proNum, int time) {
        progressNum = proNum;
        anim.setDuration(time);
        startAnimation(anim);
    }

    public class CircleAnim extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum;//这里计算进度条的比例
            postInvalidate();
            setRepeatCount(0);
        }
    }

}