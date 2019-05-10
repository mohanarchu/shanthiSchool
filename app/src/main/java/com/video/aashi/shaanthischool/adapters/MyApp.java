package com.video.aashi.shaanthischool.adapters;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {


        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/mont.ttf");
        super.onCreate();
    }
}