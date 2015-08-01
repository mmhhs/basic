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
import com.base.feima.baseproject.util.BaseConstant;
import com.base.feima.baseproject.util.VersionCheckUtils;

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
        VersionCheckUtils versionCheckUtils = new VersionCheckUtils(this,naviText0,taskTag);
        versionCheckUtils.checkVersion();
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




}
