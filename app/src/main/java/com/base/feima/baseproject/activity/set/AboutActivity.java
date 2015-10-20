package com.base.feima.baseproject.activity.set;


import android.os.Bundle;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseActivity;
import com.base.feima.baseproject.util.OptionUtil;

import butterknife.InjectView;


public class AboutActivity extends BaseActivity {
    @InjectView(R.id.base_ui_about_version)
    public TextView versionText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
		setContentView(R.layout.base_ui_about);
	}

    @Override
    public void init() {
        setTitleString(getString(R.string.title1));
        setBackVisibility(true);
        setTitleVisibility(true);
        versionText.setText(getString(R.string.about1) + OptionUtil.getVersionName(this));
    }

}