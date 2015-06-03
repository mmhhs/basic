package com.base.feima.baseproject.base;


import android.app.Activity;
import android.os.Bundle;

import com.base.feima.baseproject.task.BaseTask;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.manager.ScreenManager;

import butterknife.ButterKnife;


public abstract class BaseActivity extends Activity{
	public String taskTag = "BaseActivity";//当前BaseActivity的线程标识
	protected ScreenManager screenManager = ScreenManager.getScreenManagerInstance();
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screenManager.pushActivity(this);
	}

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        initView();
        initData();
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

    /**
     * 初始化视图相关操作
     */
    public abstract void initView();

    /**
     * 初始化数据相关操作
     */
    public abstract void initData();

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
     * 关闭当前Activity中所有还在运行的线程
     */
	protected void cancelTasks(){
		taskManager.cancelLimitTasks(taskTag);
	}

    /**
     * 关闭当前Activity
     */
	public void finishSelf(){
		try {
			screenManager.finishActivity(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	
}