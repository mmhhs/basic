package com.base.feima.baseproject.base;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.base.feima.baseproject.task.BaseTask;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.manager.MFragmentsManager;
import com.base.feima.baseproject.manager.ScreenManager;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends FragmentActivity {
	public String taskTag = "BaseFragmentActivity";//��ǰBaseFragmentActivity���̱߳�ʶ
	protected ScreenManager screenManager = ScreenManager.getScreenManagerInstance();
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
    public MFragmentsManager mFragmentsManager = MFragmentsManager.getFragmentManagerInstance();
	
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
     * ��ʼ����ͼ��ز���
     */
    public abstract void initView();

    /**
     * ��ʼ��������ز���
     */
    public abstract void initData();

    /**
     * ����̵߳��̹߳�����
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
     * �رյ�ǰFragmentActivity�����л������е��߳�
     */
	protected void cancelTasks(){
		taskManager.cancelLimitTasks(taskTag);
	}

    /**
     * ��ȡ�̱߳�ʶ
     * @return
     */
	public String getTaskTag() {
		return taskTag;
	}

    /**
     * �����̱߳�ʶ
     * @param taskTag
     */
	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}

    /**
     * ɾ������Fragment
     */
	public void removeAllFragments(){
        mFragmentsManager.removeAllFragment(getSupportFragmentManager());
    }
}