package com.feima.baseproject.daemon;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.feima.baseproject.util.tool.LogUtil;


public class KeepLiveService extends Service {
    private static Service mKeepLiveService;
    private KeepLiveReceiver keepLiveReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mKeepLiveService = this;
        startService(new Intent(this, InnerService.class));
        registerBroadcast();
        KeepLiveManager.getInstance().addAccount();
        LogUtil.e("--------------KeepLiveService startService--------------");
        return Service.START_STICKY;
    }


    public boolean onStartJob(JobParameters params) {
        return false;
    }

    public static class InnerService extends Service{

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtil.e("--------------InnerService startService--------------");
            KeepLiveManager.getInstance().setForeground(mKeepLiveService,this);
            return super.onStartCommand(intent,flags,startId);
        }


    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(keepLiveReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void registerBroadcast(){
        if (keepLiveReceiver == null){
            keepLiveReceiver = new KeepLiveReceiver();
            IntentFilter recevierFilter=new IntentFilter();
            recevierFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
            recevierFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
            recevierFilter.addAction(Intent.ACTION_POWER_CONNECTED);
            recevierFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            recevierFilter.addAction(Intent.ACTION_USER_PRESENT);
            recevierFilter.addAction(Intent.ACTION_SCREEN_ON);
            recevierFilter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(keepLiveReceiver, recevierFilter);
        }
    }

}
