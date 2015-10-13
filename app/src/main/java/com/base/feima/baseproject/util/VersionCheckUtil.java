package com.base.feima.baseproject.util;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.listener.IOnDialogResultListener;
import com.base.feima.baseproject.listener.IOnIgnoreListener;
import com.base.feima.baseproject.listener.IOnProgressListener;
import com.base.feima.baseproject.listener.IOnSureListener;
import com.base.feima.baseproject.model.version.VersionResult;
import com.base.feima.baseproject.task.FileDownLoadAsyncTask;
import com.base.feima.baseproject.task.ShowDialogTask;
import com.base.feima.baseproject.task.TaskConstant;
import com.base.feima.baseproject.util.tool.JacksonUtil;
import com.base.feima.baseproject.util.popupwindow.PopupwindowUtil;
import com.base.feima.baseproject.util.net.HttpUtil;

import java.io.File;
import java.util.Map;

public class VersionCheckUtil {
    private Activity activity;
    private View parentView;
    private String taskTag;
    private VersionResult.VersionDataEntity versionDataEntity;
    private boolean showToast = false;

    public VersionCheckUtil(Activity activity, View parentView, String taskTag) {
        this.activity = activity;
        this.parentView = parentView;
        this.taskTag = taskTag;
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
                                PopupwindowUtil popupwindowTool = new PopupwindowUtil(activity);
                                popupwindowTool.showSureWindow(activity, parentView, activity.getString(R.string.tip5), message, true, true, false, 0);
                                popupwindowTool.setiOnSureListener(new IOnSureListener() {
                                    @Override
                                    public void onSureClick() {
                                        File apkFile = new File(BaseConstant.APKPATH);
                                        boolean needDownLoad = true;
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
                                });
                                popupwindowTool.setiOnIgnoreListener(new IOnIgnoreListener() {
                                    @Override
                                    public void onIgnoreClick() {
                                        SharedUtil.saveIgnoreVersion(activity, ""+versionDataEntity.getVersionCode());
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
        task.setiOnDialogBackgroundListener(new IOnDialogBackgroundListener() {
            @Override
            public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask) {
                BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
                JacksonUtil json = JacksonUtil.getInstance();
                VersionResult res = json.readValue(showDialogTask.getResultsString(), VersionResult.class);
                if(res!=null){
                    if(ResultUtil.judgeResult(activity, res.getCode())){
                        taskResult = BaseConstant.TaskResult.OK;
                        versionDataEntity = res.getData();
                    }else{
                        taskResult = BaseConstant.TaskResult.ERROR;
                        showDialogTask.setErrorMsg(res.getCode());
                    }
                }else{
                    taskResult = BaseConstant.TaskResult.CANCELLED;
                }
                return taskResult;
            }
        });
        task.setiOnDialogResultListener(new IOnDialogResultListener() {
            @Override
            public void onOK(ShowDialogTask showDialogTask) {
                updateVersion();
            }

            @Override
            public void onError(ShowDialogTask showDialogTask) {

            }

            @Override
            public void onDone(ShowDialogTask showDialogTask) {

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
}