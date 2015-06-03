package com.base.feima.baseproject.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity����
 * @author mmh
 * 2015.02.08
 */
public class ScreenManager {
	private static List<Activity> activityList = new ArrayList<Activity>();
	private static ScreenManager instance;

	private ScreenManager() {
	}

	public static synchronized ScreenManager getScreenManagerInstance() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	/**
	 * �˳�ջ��Activity
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityList.remove(activity);
			activity = null;
		}
	}

	/**
	 * ��õ�ǰջ��Activity
	 * @return
	 */
	public Activity getCurrentActivity() {
		if(activityList.size()>0){
			Activity activity = activityList.get(activityList.size()-1);
			return activity;
		}else{
			return null;
		}
		
	}

	/**
	 * ����ǰActivity����ջ��
	 * @param activity
	 */	
	public void pushActivity(Activity activity) {		
		activityList.add(activity);
	}

	/**
	 * �˳�ջ������Activity����һ��Activity
	 */	
	public void finishAllActivityExceptOne(Class cls) {
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null&&!activity.getClass().equals(cls)) {
					finishActivity(activity);
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
		}
		
	}
	
	/**
	 * �˳�ջ������Activity
	 */	
	public void finishAllActivity() {
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null) {
					finishActivity(activity);
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
		}
	}
	
	/**
	 * �˳�ջ�е�ǰActivity
	 */	
	public void finishCurrentActivity() {
		try {
			Activity activity = getCurrentActivity();
			if (activity == null) {
				return;
			}			
			finishActivity(activity);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
	}
	
	/**
	 * �˳�ջ��ָ��Activity
	 */	
	public void finishOneActivity(Class cls) {
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null&&activity.getClass().equals(cls)) {
					finishActivity(activity);
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
		}
	}
	
}