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
	public void closeActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
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
	public void closeAllExceptOne(Class cls) {
        Activity keepActivity = null;
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null) {
                    if (activity.getClass().getSimpleName().equals(cls.getSimpleName())){
                        keepActivity = activity;
                    }else {
                        closeActivity(activity);
                    }
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
        activityList.clear();
        if (keepActivity!=null){
            activityList.add(keepActivity);
        }
	}

    /**
     * �˳�ջ������Activity����ָ��Activity
     * @param cls
     */
    public void closeAllExcept(Class[] cls) {
        Activity[] activities = new Activity[cls.length];
        for(int i=0;i<activityList.size();i++){
            try {
                Activity activity = activityList.get(i);
                if (activity != null) {
                    boolean canClose = true;
                    for (int c=0;c<cls.length;c++){
                        if (activity.getClass().getSimpleName().equals(cls[c].getSimpleName())){
                            canClose = false;
                            activities[c] = activity;
                        }
                    }
                    if (canClose){
                        closeActivity(activity);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        activityList.clear();
        for (int i=0;i<activities.length;i++){
            if (activities[i]!=null){
                activityList.add(activities[i]);
            }
        }

    }
	
	/**
	 * �˳�ջ������Activity
	 */	
	public void closeAll() {
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null) {
					closeActivity(activity);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �˳�ջ��activity
	 */	
	public void closeCurrent() {
		try {
			Activity activity = getCurrentActivity();
			if (activity == null) {
				return;
			}			
			closeActivity(activity);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
	}
	
	/**
	 * �˳�ջ��ָ��Activity
	 */	
	public void closeAppoin(Class cls) {
		for(int i=0;i<activityList.size();i++){
			try {
				Activity activity = activityList.get(i);
				if (activity != null&&activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
					closeActivity(activity);
                    activityList.remove(activity);
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
}