package com.base.feima.baseproject.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.base.feima.baseproject.task.BaseTask;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.manager.MFragmentsManager;


public abstract class BaseFragment extends Fragment {
	public String taskTag = "BaseFragment";//��ǰFragment���̱߳�ʶ
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
    public MFragmentsManager mFragmentsManager = MFragmentsManager.getFragmentManagerInstance();


	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mFragmentsManager.addFragment(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
	public void onPause(){
		super.onPause();
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
	public void onDestroy(){
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
     * �رյ�ǰFragment�����л������е��߳�
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


	
	
	
}