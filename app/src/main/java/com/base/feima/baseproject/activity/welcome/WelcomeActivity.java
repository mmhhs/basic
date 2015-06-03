package com.base.feima.baseproject.activity.welcome;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.activity.welcome.WelcomeFragment.IntentOnClickListener;
import com.base.feima.baseproject.base.BaseFragmentActivity;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.util.SharedUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;

public class WelcomeActivity extends BaseFragmentActivity {
    @InjectView(R.id.base_ui_welcome_imageView)
    public ImageView imageView ;
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
        super.onDestroy();
        try {
            if(timer!=null){
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(){
        fMgr = getSupportFragmentManager();
        Boolean isFirst = SharedUtil.getHelpStatus(getApplicationContext());
        int versionHelp = SharedUtil.getHelpCode(getApplicationContext());
        int versionCurrent = 1;
        try {
            versionCurrent = PublicTools.getVersionCode(getApplicationContext());
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
		welcomeFragment.setIntentOnClickListener(new IntentOnClickListener(){

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
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
		timer.schedule(task, 2000);
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
	
	private void setIntent(){
		try {
			Intent intent = new Intent();				 
			intent.setClass(WelcomeActivity.this, WelcomeActivity.class);
			startActivity(intent);	
			finish();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


	
	//点击返回按钮
	@Override
	public void onBackPressed() {		
		finish();
	}
	

}
