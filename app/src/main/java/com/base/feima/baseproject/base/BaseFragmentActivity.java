package com.base.feima.baseproject.base;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.manager.MFragmentsManager;
import com.base.feima.baseproject.manager.ScreenManager;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.task.BaseTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

public abstract class BaseFragmentActivity extends FragmentActivity {
	public String taskTag = "BaseFragmentActivity";//当前BaseFragmentActivity的线程标识
	protected ScreenManager screenManager = ScreenManager.getScreenManagerInstance();
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public MFragmentsManager mFragmentsManager = MFragmentsManager.getFragmentManagerInstance();
	@Optional
	@InjectView(R.id.base_ui_title_back_layout)
	public LinearLayout backLayout;
	@Optional
	@InjectView(R.id.base_ui_title_title)
	public TextView titleText;
	@Optional
	@InjectView(R.id.base_view_contain_layout)
	public LinearLayout containLayout;
	@Optional
	@InjectView(R.id.base_view_load_layout)
	public LinearLayout loadLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screenManager.pushActivity(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
		init();
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
		cancelTasks();
		super.onDestroy();
	}

	/**
	 * 初始化相关操作
	 */
	public abstract void init();

	/**
	 * 添加线程到线程管理中
	 * @param task
	 */
	protected void addTask(BaseTask task){
		try {
			taskManager.addTask(taskTag, task);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 关闭当前FragmentActivity中所有还在运行的线程
	 */
	protected void cancelTasks(){
		taskManager.cancelLimitTasks(taskTag);
	}

	/**
	 * 获取线程标识
	 * @return
	 */
	public String getTaskTag() {
		return taskTag;
	}

	/**
	 * 设置线程标识
	 * @param taskTag
	 */
	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}

	/**
	 * 删除所有Fragment
	 */
	public void removeAllFragments(){
		mFragmentsManager.removeAllFragment(getSupportFragmentManager());
	}

	/**
	 * 关闭当前Activity
	 */
	public void finishSelf(){
		try {
			screenManager.closeActivity(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Optional
	@OnClick(R.id.base_choose_images_title_back)
	public void onBack(){
		finishSelf();
	}

	@Optional
	@OnClick(R.id.base_ui_title_back_layout)
	public void back(){
		finishSelf();
	}

	public void setTitleText(String name) {
		this.titleText.setText(name);
	}

	public void setBackLayoutVisibility(int visible){
		backLayout.setVisibility(visible);
	}

	public void setTitleTextVisibility(int visible){
		titleText.setVisibility(visible);
	}

}