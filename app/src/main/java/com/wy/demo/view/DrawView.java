package com.wy.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * Created by Administrator on 2018/8/21.
 */

public class DrawView extends View {

    public float currentX = 50;
    public float currentY = 50;

    public DrawView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, mPaint);
    }
}
