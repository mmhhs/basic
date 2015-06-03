package com.base.feima.baseproject.manager;

import com.base.feima.baseproject.task.BaseTask;

import java.util.ArrayList;
import java.util.List;

/**
 * �̹߳���
 * @author mmh
 * 2015.02.08
 */
public class TaskManager {
	public String tag = "TaskManager";
	public List<TaskModel> taskList = new ArrayList<TaskModel>();
	
	private static TaskManager instance;
	
	private TaskManager() {
	}

	public static synchronized TaskManager getTaskManagerInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

    /**
     * ����߳�
     */
	public void addTask(String tagString,BaseTask task){
		try {
			TaskModel taskModel = new TaskModel();
			taskModel.tagString = tagString;
			taskModel.task = task;
			taskList.add(taskModel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

    /**
     * �ر����л������е��߳�
     */
	public void cancelAllTasks(){
		for(int i=0;i<taskList.size();i++){
		try {			
			TaskModel taskModel = taskList.get(i);
			if(taskModel.task!=null){				
				taskModel.task.cancel(true);
				taskList.remove(taskModel);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
	}

    /**
     * �ر�tagString����ʶ��Activity����Fragment�����л������е��߳�
     * @param tagString
     */
	public void cancelLimitTasks(String tagString){
		for(int i=0;i<taskList.size();i++){
		try {			
			TaskModel taskModel = taskList.get(i);			
			if(taskModel.tagString.equals(tagString)&&taskModel.task!=null){				
				taskModel.task.cancel(true);
				taskList.remove(taskModel);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
	}

    /**
     * �ر�һ���������е��߳�
     */
	public void cancelOneTasks(BaseTask task){
		for(int i=0;i<taskList.size();i++){
		try {			
			TaskModel taskModel = taskList.get(i);			
			if(taskModel.task!=null&&taskModel.task==task){				
				taskModel.task.cancel(true);
				taskList.remove(taskModel);
				break;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
	}


	public class TaskModel{
		String tagString;
		BaseTask task;
	}
	
	
}