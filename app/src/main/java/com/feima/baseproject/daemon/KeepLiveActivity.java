package com.feima.baseproject.daemon;


import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.feima.baseproject.base.BaseActivity;
import com.feima.baseproject.util.tool.LogUtil;


public class KeepLiveActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
        LogUtil.e("--------------KeepLiveActivity onCreate--------------");
        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);
	}

    @Override
    public void init() {

    }

}