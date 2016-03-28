package com.feima.baseproject.base;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.util.image.fresco.FrescoUtils;


public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseConstant.initDebug();
        FrescoUtils.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}