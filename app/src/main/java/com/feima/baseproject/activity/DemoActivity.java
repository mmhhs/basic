package com.base.feima.baseproject.activity;


import android.os.Bundle;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseActivity;


public class DemoActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
		setContentView(R.layout.base_fragment_welcome);
	}

    @Override
    public void init() {

    }

}