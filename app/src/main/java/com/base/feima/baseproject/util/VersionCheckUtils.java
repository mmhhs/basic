package com.base.feima.baseproject.util;

import android.app.Activity;
import android.view.View;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.listener.IOnDialogResultListener;
import com.base.feima.baseproject.listener.IOnProgressListener;
import com.base.feima.baseproject.listener.IOnSureListener;
import com.base.feima.baseproject.model.version.VersionResult;
import com.base.feima.baseproject.task.FileDownLoadAsyncTask;
import com.base.feima.baseproject.task.ShowDialogTask;
import com.base.feima.baseproject.task.TaskConstant;
import com.base.feima.baseproject.tool.EncryTools;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.ResultTools;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool;

import java.io.File;
import java.util.Map;

public class VersionCheckUtils {
    private Activity activity;
    private View parentView;
    private String taskTag;
    private VersionResult.VersionDataEntity versionDataEntity;

    public VersionCheckUtils(Activity activity, View parentView, String taskTag) {
        this.activity = activity;
        this.parentView = parentView;
        this.taskTag = taskTag;
    }

    private void updateVersion(){
        try {
            if (versionDataEntity!=null){
                if (!versionDataEntity.getVersionCode().equals(""+ PublicTools.getVersionCode(activity))){
                    PopupwindowTool popupwindowTool = new PopupwindowTool(activity);
                    popupwindowTool.showSureWindow(activity, parentView, activity.getString(R.string.dialog_item6), ""+versionDataEntity.getUpdateInfo(), true, true, false, 0);
                    popupwindowTool.setiOnSureListener(new IOnSureListener() {
                        @Override
                        public void onSureClick() {
                            File apkFile = new File(BaseConstant.APKPATH);
                            boolean needDownLoad = true;
                            if (apkFile.exists()){
                                int versionCode = PublicTools.getApkVersionCode(activity,BaseConstant.APKPATH);
                                if (versionDataEntity.getVersionCode().equals(""+versionCode)){
                                    needDownLoad = false;
                                }
                            }
                            if (needDownLoad){
                                downLoadApk(versionDataEntity.getDownload(), BaseConstant.APKPATH);
                            }else {
                                PublicTools.install(activity,BaseConstant.APKPATH);
                            }
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    
    public void checkVersion(boolean showDialog){
        String httpUrl = BaseConstant.VERSION;
        Map<String,Object> argMap = EncryTools.getEncryMap();
        argMap.put("version",PublicTools.getVersionName(activity));
        argMap.put("versionCode",""+PublicTools.getVersionCode(activity));
        argMap.put("deviceType","android");
        ShowDialogTask task = new ShowDialogTask(activity,taskTag,parentView, activity.getString(R.string.dialog_item6),showDialog,httpUrl,argMap, TaskConstant.POST);
        task.setiOnDialogBackgroundListener(new IOnDialogBackgroundListener() {
            @Override
            public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask) {
                BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
                JacksonUtil json = JacksonUtil.getInstance();
                VersionResult res = json.readValue(showDialogTask.getResultsString(), VersionResult.class);
                if(res!=null){
                    if(ResultTools.judgeResult(activity, res.getCode())){
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
        FileDownLoadAsyncTask fileDownLoadAsyncTask = new FileDownLoadAsyncTask(activity,updateUrl,storePath,false,parentView, activity.getString(R.string.dialog_item6));
        fileDownLoadAsyncTask.setiOnProgressListener(new IOnProgressListener() {
            @Override
            public void start() {

            }

            @Override
            public void transferred(String transferedBytes, long totalBytes) {
            }

            @Override
            public void success(String fileStorePath) {
                PublicTools.install(activity,fileStorePath);
            }

            @Override
            public void error(String tip) {
                PublicTools.addToast(activity,tip);
            }

            @Override
            public void done() {

            }
        });
        fileDownLoadAsyncTask.execute();
    }
}