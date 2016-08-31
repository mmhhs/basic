package com.feima.baseproject.daemon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.feima.baseproject.util.tool.LogUtil;

/**
 * This Service is Persistent Service. Do some what you want to do here.<br/>
 *
 * Created by Mars on 12/24/15.
 */
public class Service1 extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO do some thing what you want..
        LogUtil.e("--------------Service1--------------");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
