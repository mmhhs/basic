package com.base.feima.baseproject.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.task.BaseTask;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.manager.ScreenManager;
import com.widget.slidingmenu.lib.SlidingMenu;
import com.widget.slidingmenu.lib.app.SlidingFragmentActivity;


public class BaseSlideFragmentActivity extends SlidingFragmentActivity {
	public String tagString = "BaseSlideFragmentActivity";
	protected ScreenManager screenManager = ScreenManager.getScreenManagerInstance();
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	protected SlidingMenu slidingMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screenManager.pushActivity(this);
		
		setBehindContentView(R.layout.base_slide_frame_menu);
		slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
        slidingMenu.setShadowDrawable(R.drawable.base_slide_shadow);
        slidingMenu.setSecondaryShadowDrawable(R.drawable.base_slide_shadow_right);
        slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() / 5);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.setFadeDegree(0.4f);
        slidingMenu.setBehindScrollScale(0);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setSecondaryMenu(R.layout.base_slide_frame_right);
	}
	
	@Override
	protected void onPause(){		
		super.onPause();
	}
	
	@Override
	protected void onResume(){		
		super.onResume();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		cancelTasks();
	}
	
	protected void addTask(BaseTask task){
		try {
			taskManager.addTask(tagString, task);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}
	
	protected void cancelTasks(){
		taskManager.cancelLimitTasks(tagString);
	}

	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}
	
	/**
	 * 设置侧滑菜单1
	 * @param fragment
	 */
	public void setBehindView(Fragment fragment) {
		
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.layout.base_slide_frame_menu, fragment);
        t.commit();
	}	
	
	/**
	 * 设置侧滑菜单2
	 * @param fragment
	 */
	public void setSecondaryView(Fragment fragment) {
		
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.layout.base_slide_frame_right, fragment);
        t.commit();
	}
	

	/**
	 * 设置侧滑模式 默认是SlidingMenu.LEFT_RIGHT;
	 * @param mode
	 */
	public void setSlideMode(int mode) {
		slidingMenu.setMode(mode);
	}
	
}