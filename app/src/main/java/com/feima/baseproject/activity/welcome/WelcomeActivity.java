package com.feima.baseproject.activity.welcome;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feima.baseproject.R;
import com.feima.baseproject.activity.HomeActivity;
import com.feima.baseproject.base.BaseFragmentActivity;
import com.feima.baseproject.listener.IOnClickListener;
import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.util.SharedUtil;
import com.feima.baseproject.util.image.fresco.FrescoUtils;
import com.feima.baseproject.util.image.fresco.InstrumentedDraweeView;
import com.feima.baseproject.util.tool.StringUtil;
import com.feima.baseproject.view.widget.spin.RoundProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;
import permissions.dispatcher.PermissionRequest;

public class WelcomeActivity extends BaseFragmentActivity {
	@InjectView(R.id.base_ui_welcome_imageView)
	public ImageView imageView ;
	@InjectView(R.id.base_ui_welcome_ad)
	public InstrumentedDraweeView adView ;
	@InjectView(R.id.base_ui_welcome_progressBar_bg)
	public ImageView progressBg;
	@InjectView(R.id.base_ui_welcome_progressBar)
	public RoundProgressBar progressBar;
	@InjectView(R.id.base_ui_welcome_fragmentRoot)
	public LinearLayout proLinear;
	private Handler handler;
	private Timer timer;

	private static FragmentManager fMgr;
	private final String tabTag1 = "WelcomeFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		setTaskTag(getClass().getSimpleName());
		setContentView(R.layout.base_ui_welcome);
	}

	@Override
	protected void onDestroy(){
		stop();
		super.onDestroy();
	}

	@Override
	public void init(){
		fMgr = getSupportFragmentManager();
		Boolean isFirst = SharedUtil.getHelpStatus(getApplicationContext());
		int versionHelp = SharedUtil.getHelpCode(getApplicationContext());
		int versionCurrent = 1;
		try {
			versionCurrent = OptionUtil.getVersionCode(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isFirst){
			SharedUtil.saveHelpStatus(getApplicationContext(), false, versionCurrent);
			initFragment();
		}else{
			if(versionHelp<versionCurrent){
				SharedUtil.saveHelpStatus(getApplicationContext(), false, versionCurrent);
				initFragment();
			}else{
				if (!StringUtil.isEmpty(SharedUtil.getAdImage(this))){
					progressBar.startCountdown();
					progressBar.setVisibility(View.VISIBLE);
					progressBg.setVisibility(View.VISIBLE);
					FrescoUtils.displayImage(adView, SharedUtil.getAdImage(this), BaseConstant.SCALE_WIDTH, BaseConstant.SCALE_HEIGHT);
				}else {
					progressBar.setVisibility(View.GONE);
					progressBg.setVisibility(View.GONE);
				}
				initTimerTask();
			}
		}

	}

	/**
	 * 初始化首个Fragment
	 */
	private void initFragment() {
		FragmentTransaction ft = fMgr.beginTransaction();
		WelcomeFragment welcomeFragment = new WelcomeFragment();
		welcomeFragment.setiOnClickListener(new IOnClickListener() {
			@Override
			public void onClick(int index) {
				setIntent();
			}
		});
		ft.add(R.id.base_ui_welcome_fragmentRoot, welcomeFragment, tabTag1);
		ft.commit();
	}

	private void initTimerTask(){
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}

		};
		timer = new Timer(true);
		timer.schedule(task, 3000);
		handler = new Handler(){
			public void handleMessage(Message msg) {
				//activity
				try {
					if(timer!=null){
						timer.cancel();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				setIntent();
			}
		};
	}

	@OnClick(R.id.base_ui_welcome_progressBar)
	public void setIntent(){
		try {
			Intent intent = new Intent();
			intent.setClass(WelcomeActivity.this, HomeActivity.class);
			startActivity(intent);
			finishSelf();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	boolean isStop = false;
	private void stop(){
		try {
			if (!isStop){
				if(timer!=null){
					timer.cancel();
				}
				progressBar.stopCountdown();
				isStop = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//点击返回按钮
	@Override
	public void onBackPressed() {
		finishSelf();
	}

	@Override
	public void doACacheNeedsPermission() {

	}

	@Override
	public void ACacheShowRationale(PermissionRequest request) {

	}

	@Override
	public void ACacheOnPermissionDenied() {

	}

	@Override
	public void ACacheOnNeverAskAgain() {

	}

}
