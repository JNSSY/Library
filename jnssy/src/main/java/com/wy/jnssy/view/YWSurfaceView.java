package com.wy.jnssy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class YWSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private boolean isDrawing = false;
    private SurfaceHolder holder;
    private Canvas canvas;

    public YWSurfaceView(Context context) {
        this(context, null);
    }

    public YWSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YWSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);            //是否可以获得焦点
        setFocusableInTouchMode(true); //能否通过触摸获得焦点
        setKeepScreenOn(true);         //保持屏幕常亮
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new DrawThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    class DrawThread extends Thread {
        @Override
        public void run() {
            while (isDrawing) {
                drawView();
            }
        }
    }

    private void drawView() {
        try {
            canvas = holder.lockCanvas();


        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }


    }
}
