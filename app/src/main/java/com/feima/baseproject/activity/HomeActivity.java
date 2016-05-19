package com.feima.baseproject.activity;


import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseFragmentActivity;
import com.feima.baseproject.fragment.DemoFragment;
import com.feima.baseproject.listener.IOnResultListener;
import com.feima.baseproject.task.DispatchTask;
import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.util.PermissionUtil;
import com.feima.baseproject.util.SharedUtil;
import com.feima.baseproject.util.VersionCheckUtil;
import com.feima.baseproject.util.image.fresco.FrescoUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;


public class HomeActivity extends BaseFragmentActivity {
    @InjectView(android.R.id.tabhost)
    public FragmentTabHost mTabHost;
    @InjectView(R.id.base_ui_home_radio0)
    public TextView naviText0;
    @InjectView(R.id.base_ui_home_radio1)
    public TextView naviText1;
    @InjectView(R.id.base_ui_home_radio2)
    public TextView naviText2;
    public int tabFlag = 0;

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
    public void init(){
        mTabHost.setup(this, getSupportFragmentManager(), R.id.base_ui_home_frame);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
			/* 为每一个Tab按钮设置图标、文字和内容 */
            TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			/* 将Tab按钮添加进Tab选项卡中 */
            mTabHost.addTab(tabSpec, fragments[i], null);
        }
        mTabHost.setCurrentTab(0);
        VersionCheckUtil versionCheckUtils = new VersionCheckUtil(this,naviText0,taskTag);
        versionCheckUtils.checkVersion(false);
        versionCheckUtils.setiOnResultListener(new IOnResultListener() {
            @Override
            public void onOK(DispatchTask task) {
                //adPreDraweeView 广告图片预加载
                FrescoUtils.displayImage(null, SharedUtil.getAdImage(HomeActivity.this), 10, 10);
            }

            @Override
            public void onError(DispatchTask task) {

            }

            @Override
            public void onDone(DispatchTask task) {

            }
        });
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

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void doACacheNeedsPermission() {

    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void ACacheShowRationale(PermissionRequest request) {
        request.proceed(); // 提示用户权限使用的对话框
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void ACacheOnPermissionDenied() {
        PermissionUtil.showPermissionDialog(this,naviText0);
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void ACacheOnNeverAskAgain() {
        PermissionUtil.showPermissionDialog(this, naviText0);
    }

    long firstTime = 0;

    @Override
    public void onBackPressed() {
        try {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                OptionUtil.addToast(this, getString(R.string.quit));
                firstTime = secondTime;
            } else {
                finishSelf();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
