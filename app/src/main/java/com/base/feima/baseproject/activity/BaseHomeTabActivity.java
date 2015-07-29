package com.base.feima.baseproject.activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseFragmentActivity;
import com.base.feima.baseproject.fragment.DemoFragment;
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
import com.base.feima.baseproject.util.BaseConstant;
import com.base.feima.baseproject.util.JacksonUtil;

import java.io.File;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


public class BaseHomeTabActivity extends BaseFragmentActivity{
    @InjectView(android.R.id.tabhost)
    public FragmentTabHost mTabHost;
    @InjectView(R.id.base_ui_home_radio0)
    public TextView naviText0;
    @InjectView(R.id.base_ui_home_radio1)
    public TextView naviText1;
    @InjectView(R.id.base_ui_home_radio2)
    public TextView naviText2;
    public int tabFlag = 0;
    public String updateUrl = "";

	private final Class[] fragments = { DemoFragment.class, DemoFragment.class,
            DemoFragment.class };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
	        return; 
	    }
        setTaskTag(getClass().getSimpleName());
        setContentView(R.layout.base_ui_home);
    }
       
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {      
		super.onConfigurationChanged(newConfig);
    }

    @Override
    public void initView(){
        mTabHost.setup(this, getSupportFragmentManager(), R.id.base_ui_home_frame);
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			/* 为每一个Tab按钮设置图标、文字和内容 */
			TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			/* 将Tab按钮添加进Tab选项卡中 */
			mTabHost.addTab(tabSpec, fragments[i], null);
		}
		mTabHost.setCurrentTab(0);
    }

    @Override
    public void initData(){

    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        int index = intent.getIntExtra(BaseConstant.INTENT_TYPE,0);
        setCurrentScreen(index);
    }

    @OnClick({R.id.base_ui_home_radio0,R.id.base_ui_home_radio1,R.id.base_ui_home_radio2})
    public void onNaviSelected(View view){
        switch (view.getId()){
            case R.id.base_ui_home_radio0:
                setCurrentScreen(0);
                tabFlag = 0;
                break;
            case R.id.base_ui_home_radio1:
                setCurrentScreen(1);
                tabFlag = 1;
                break;
            case R.id.base_ui_home_radio2:
                setCurrentScreen(2);
                tabFlag = 2;
                break;
            default:
                break;
        }
    }

    private void setCurrentScreen(int index){
        mTabHost.setCurrentTab(index);
        naviText0.setSelected(false);
        naviText1.setSelected(false);
        naviText2.setSelected(false);
        switch (index){
            case 0:
                naviText0.setSelected(true);
                break;
            case 1:
                naviText1.setSelected(true);
                break;
            case 2:
                naviText2.setSelected(true);
                break;
            default:
                break;
        }
    }


    private void updateVersion(){
        try {
            if (versionDataEntity!=null){
                if (!versionDataEntity.getVersionCode().equals(""+PublicTools.getVersionCode(BaseHomeTabActivity.this))){
                    PopupwindowTool popupwindowTool = new PopupwindowTool();
                    popupwindowTool.showSureWindow(BaseHomeTabActivity.this, naviText0, getString(R.string.dialog_item6), ""+versionDataEntity.getUpdateInfo(), true, true, false, 0);
                    popupwindowTool.setiOnSureListener(new IOnSureListener() {
                        @Override
                        public void onSureClick() {
                            File apkFile = new File(BaseConstant.APKPATH);
                            boolean needDownLoad = true;
                            if (apkFile.exists()){
                                int versionCode = PublicTools.getApkVersionCode(BaseHomeTabActivity.this,BaseConstant.APKPATH);
                                if (versionDataEntity.getVersionCode().equals(""+versionCode)){
                                    needDownLoad = false;
                                }
                            }
                            if (needDownLoad){
                                downLoadApk(versionDataEntity.getDownload(), BaseConstant.APKPATH);
                            }else {
                                PublicTools.install(BaseHomeTabActivity.this,BaseConstant.APKPATH);
                            }
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private VersionResult.VersionDataEntity versionDataEntity;
    public void setCheckVersion(){
        String httpUrl = BaseConstant.VERSION;
        Map<String,Object> argMap = EncryTools.getEncryMap();
        argMap.put("version",PublicTools.getVersionName(this));
        argMap.put("versionCode",""+PublicTools.getVersionCode(this));
        argMap.put("deviceType","android");
        ShowDialogTask task = new ShowDialogTask(this,taskTag,naviText0,getString(R.string.dialog_item6),false,httpUrl,argMap, TaskConstant.POST);
        task.setiOnDialogBackgroundListener(new IOnDialogBackgroundListener() {
            @Override
            public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask) {
                BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
                JacksonUtil json = JacksonUtil.getInstance();
                VersionResult res = json.readValue(showDialogTask.getResultsString(), VersionResult.class);
                if(res!=null){
                    if(ResultTools.judgeResult(BaseHomeTabActivity.this, res.getCode())){
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
        FileDownLoadAsyncTask fileDownLoadAsyncTask = new FileDownLoadAsyncTask(this,updateUrl,storePath,false,naviText0,getString(R.string.dialog_item6));
        fileDownLoadAsyncTask.setiOnProgressListener(new IOnProgressListener() {
            @Override
            public void start() {

            }

            @Override
            public void transferred(String transferedBytes, long totalBytes) {
            }

            @Override
            public void success(String fileStorePath) {
                PublicTools.install(BaseHomeTabActivity.this,fileStorePath);
            }

            @Override
            public void error(String tip) {
                PublicTools.addToast(BaseHomeTabActivity.this,tip);
            }

            @Override
            public void done() {

            }
        });
        fileDownLoadAsyncTask.execute();
    }
      
}
