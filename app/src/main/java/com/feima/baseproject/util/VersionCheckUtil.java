package com.feima.baseproject.util;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnDialogListener;
import com.feima.baseproject.listener.IOnDismissListener;
import com.feima.baseproject.listener.IOnProgressListener;
import com.feima.baseproject.listener.IOnResultListener;
import com.feima.baseproject.manager.ScreenManager;
import com.feima.baseproject.model.version.VersionResultEntity;
import com.feima.baseproject.task.DispatchTask;
import com.feima.baseproject.task.FileDownLoadAsyncTask;
import com.feima.baseproject.task.ShowDialogTask;
import com.feima.baseproject.task.TaskConstant;
import com.feima.baseproject.util.net.HttpUtil;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.io.File;
import java.util.Map;

public class VersionCheckUtil {
    private Activity activity;
    private View parentView;
    private String taskTag;
    private VersionResultEntity.VersionDataEntity versionDataEntity;
    private boolean showToast = false;
    private ScreenManager screenManager;
    private IOnResultListener iOnResultListener;
    private boolean needDownLoad;

    public VersionCheckUtil(Activity activity, View parentView, String taskTag) {
        this.activity = activity;
        this.parentView = parentView;
        this.taskTag = taskTag;
        screenManager = ScreenManager.getScreenManagerInstance();
    }

    private void updateVersion(){
        try {
            if (versionDataEntity!=null){
                if (!versionDataEntity.getVersionCode().equals(""+ OptionUtil.getVersionCode(activity))){
                    boolean showDialog = true;
                    if (versionDataEntity.getVersionCode().equals(SharedUtil.getIgnoreVersion(activity))){
                        showDialog = false;
                    }
                    if (showToast){
                        showDialog = true;
                    }
                    if (showDialog){
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                String message = ""+activity.getString(R.string.tip1)+versionDataEntity.getVersion();
                                String info = (""+versionDataEntity.getUpdateInfo()).replace("#", activity.getString(R.string.tip2));
                                message = message + activity.getString(R.string.tip2) + info;
                                DialogUtil dialogUtil = new DialogUtil(activity);
                                dialogUtil.showTipDialog( parentView, message);
                                dialogUtil.setiOnDialogListener(new IOnDialogListener() {
                                    @Override
                                    public void onConfirm() {
                                        File apkFile = new File(BaseConstant.APKPATH);
                                        needDownLoad = true;
                                        if (apkFile.exists()) {
                                            int versionCode = OptionUtil.getApkVersionCode(activity, BaseConstant.APKPATH);
                                            if (versionDataEntity.getVersionCode().equals("" + versionCode)) {
                                                needDownLoad = false;
                                            }
                                        }
                                        if (needDownLoad) {
                                            downLoadApk(versionDataEntity.getDownload(), BaseConstant.APKPATH);
                                        } else {
                                            OptionUtil.install(activity, BaseConstant.APKPATH);
                                        }
                                    }

                                    @Override
                                    public void onCancel() {
                                        if (versionDataEntity.getForceUpdate()==1){
                                            screenManager.closeAll();
                                        }
                                    }

                                    @Override
                                    public void onOther() {
                                        if (versionDataEntity.getForceUpdate()==1){
                                            screenManager.closeAll();
                                        }else {
                                            SharedUtil.saveIgnoreVersion(activity, ""+versionDataEntity.getVersionCode());
                                        }
                                    }
                                });
                                dialogUtil.setiOnDismissListener(new IOnDismissListener() {
                                    @Override
                                    public void onDismiss() {
                                        if (versionDataEntity.getForceUpdate()==1&&!needDownLoad){
                                            screenManager.closeAll();
                                        }
                                    }
                                });
                            }
                        }, 500);
                    }
                }else {
                    if (showToast){
                        OptionUtil.addToast(activity, activity.getString(R.string.tip3));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if (showToast){
                OptionUtil.addToast(activity, activity.getString(R.string.tip3));
            }
        }
    }

    
    public void checkVersion(boolean showDialog){
        String httpUrl = BaseConstant.VERSION;
        Map<String,Object> argMap = HttpUtil.getEncryMap();
        argMap.put("version", OptionUtil.getVersionName(activity));
        argMap.put("versionCode",""+ OptionUtil.getVersionCode(activity));
        argMap.put("deviceType","android");
        ShowDialogTask task = new ShowDialogTask(activity,taskTag,parentView, activity.getString(R.string.tip5),showDialog,httpUrl,argMap, TaskConstant.POST);
        task.setParseClass(VersionResultEntity.class);
        task.setiOnResultListener(new IOnResultListener() {
            @Override
            public void onOK(DispatchTask task) {
                if(task.resultEntity instanceof VersionResultEntity){
                    VersionResultEntity res = (VersionResultEntity)task.resultEntity;
                    versionDataEntity = res.getData();
                }
                updateVersion();
                if (iOnResultListener!=null){
                    iOnResultListener.onOK(task);
                }
            }

            @Override
            public void onError(DispatchTask task) {
                if (iOnResultListener!=null){
                    iOnResultListener.onError(task);
                }
            }

            @Override
            public void onDone(DispatchTask task) {
                if (iOnResultListener!=null){
                    iOnResultListener.onDone(task);
                }
            }
        });
        task.execute();
    }

    private void downLoadApk(String updateUrl, String storePath){
        FileDownLoadAsyncTask fileDownLoadAsyncTask = new FileDownLoadAsyncTask(activity,updateUrl,storePath,false,parentView, activity.getString(R.string.tip5));
        fileDownLoadAsyncTask.setiOnProgressListener(new IOnProgressListener() {
            @Override
            public void start() {

            }

            @Override
            public void transferred(String transferedBytes, long totalBytes) {
            }

            @Override
            public void success(String fileStorePath) {
                OptionUtil.install(activity, fileStorePath);
            }

            @Override
            public void error(String tip) {
                OptionUtil.addToast(activity, tip);
            }

            @Override
            public void done() {

            }
        });
        fileDownLoadAsyncTask.execute();
    }

    public void setiOnResultListener(IOnResultListener iOnResultListener) {
        this.iOnResultListener = iOnResultListener;
    }
}