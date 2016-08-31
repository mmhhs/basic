package com.feima.baseproject.base;


import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.feima.baseproject.activity.daemon.KeepLiveActivity;
import com.feima.baseproject.daemon.KeepLiveService;
import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.util.image.fresco.FrescoUtils;


public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseConstant.initDebug();
        FrescoUtils.init(this);
        baseApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        startKeepLiveService();
    }

    public static BaseApplication self(){
        return baseApplication;
    }

    public void startKeepLiveService(){
        try {
            startService(new Intent(this, KeepLiveService.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startKeepLiveActivity(){
        try {
            Intent intent = new Intent(this, KeepLiveActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}