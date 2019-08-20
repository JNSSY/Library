package com.wy.jnssy.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wy.jnssy.crash.CrashHandler;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);


        Fresco.initialize(this);
    }
}
