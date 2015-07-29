package com.base.feima.baseproject.base;


import android.app.Application;

import com.base.feima.baseproject.fresco.FrescoUtils;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FrescoUtils.init(this);
    }
}