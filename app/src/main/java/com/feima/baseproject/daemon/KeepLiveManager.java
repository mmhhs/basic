package com.feima.baseproject.daemon;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseApplication;
import com.feima.baseproject.manager.ScreenManager;
import com.feima.baseproject.util.tool.LogUtil;

public class KeepLiveManager{
    private static KeepLiveManager instance;
    private final String ACCOUNT_TYPE = BaseApplication.PACKAGE_NAME;
    private final String CONTENT_AUTHORITY = BaseApplication.PACKAGE_NAME+".provider";
    private final long SYNC_FREQUENCY = 30;
    private Account account = null;

    private KeepLiveManager() {
    }

    public static synchronized KeepLiveManager getInstance() {
        if (instance == null) {
            instance = new KeepLiveManager();
        }
        return instance;
    }

    public void startKeepLiveActivity(){
        BaseApplication.self().startKeepLiveActivity();
    }

    public void finishKeepLiveActivity(){
        ScreenManager.getScreenManagerInstance().closeAppoin(KeepLiveActivity.class);
    }

    public void startKeepLiveService(){
        BaseApplication.self().startKeepLiveService();
    }

    /**
     * 提升service优先级为前台service
     * 作用：防杀，使service不容易被系统杀死
     * 适用范围：到android6.0
     * @param keepLiveService
     * @param innerService
     */
    public void setForeground(Service keepLiveService,Service innerService){
        final int foregroundPushId = 1;
        if (keepLiveService!=null){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
                LogUtil.e("--------------JELLY_BEAN_MR2--------------");
                keepLiveService.startForeground(foregroundPushId,new Notification());
            }else {
                LogUtil.e("--------------innerService.startForeground--------------");
                keepLiveService.startForeground(foregroundPushId,new Notification());
                if (innerService!=null){
                    innerService.startForeground(foregroundPushId,new Notification());
                    innerService.stopSelf();
                }
            }
        }
    }

    /**
     * 启动JobScheduler拉活
     * 适用范围：android5.0以上
     */
    public void startJobScheduler(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                int jobId = 1;
                JobInfo.Builder builder = new JobInfo.Builder(jobId,
                        new ComponentName(BaseApplication.self(),KeepLiveService.class));
                builder.setPeriodic(10);
                builder.setPersisted(true);
                JobScheduler jobScheduler = (JobScheduler)BaseApplication.self().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(builder.build());
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 添加账户，并启用账户同步功能
     * 适用范围：所有版本
     */
    public void addAccount(){
        AccountManager accountManager = (AccountManager)BaseApplication.self().getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts!=null&&accounts.length>0){
            account = accounts[0];
        }else {
            account = new Account(BaseApplication.self().getString(R.string.account),ACCOUNT_TYPE);
        }
        if (accountManager.addAccountExplicitly(account,null,null)){
            //开启同步，并设置周期
            ContentResolver.setIsSyncable(account,CONTENT_AUTHORITY,1);
            ContentResolver.setSyncAutomatically(account,CONTENT_AUTHORITY,true);
            ContentResolver.addPeriodicSync(account,CONTENT_AUTHORITY,new Bundle(),SYNC_FREQUENCY);
            LogUtil.e("--------------开启同步，并设置周期--------------");
        }
    }

    /**
     * 手动更新
     */
    public void triggerRefresh() {
        if (account==null){
            return;
        }
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                account,
                CONTENT_AUTHORITY,
                b);
    }
}