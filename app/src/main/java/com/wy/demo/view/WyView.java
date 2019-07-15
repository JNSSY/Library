package com.wy.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class WyView extends View {

    private Paint paint;

    public WyView(Context context) {
        this(context, null);
    }

    public WyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawArc(canvas);

        invalidate();
    }


    private void drawArc(Canvas canvas) {
        RectF rectF = new RectF(0, 0, 100, 100);
        Log.e("wy", "left:" + rectF.left + "top:" + rectF.top + "right:" + rectF.right + "bottom:" + rectF.bottom);
        paint.setColor(Color.parseColor("#33333333"));
        //oval :指定圆弧的外轮廓矩形区域。
        //startAngle: 圆弧起始角度，单位为度。从180°为起始点
        //sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
        //useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。如果false会将圆弧的两端用直线连接
        //paint: 绘制圆弧的画板属性，如颜色，是否填充等
        // public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,@NonNull Paint paint)
        canvas.drawRect(rectF, paint);
        paint.setColor(Color.parseColor("#00ff00"));
        canvas.drawArc(rectF, 0, 90, true, paint);
    }
}
