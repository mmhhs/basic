package com.feima.baseproject.daemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.feima.baseproject.util.tool.LogUtil;


public class KeepLiveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)){
            LogUtil.e("--------------startKeepLiveActivity--------------");
            KeepLiveManager.getInstance().startKeepLiveActivity();
        }else if (action.equals(Intent.ACTION_USER_PRESENT)||action.equals(Intent.ACTION_SCREEN_ON)){
            LogUtil.e("--------------finishKeepLiveActivity--------------");
            KeepLiveManager.getInstance().finishKeepLiveActivity();
        }
        KeepLiveManager.getInstance().startKeepLiveService();
    }
}
