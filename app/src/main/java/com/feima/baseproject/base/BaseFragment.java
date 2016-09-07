package com.feima.baseproject.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.manager.MFragmentsManager;
import com.feima.baseproject.manager.ScreenManager;
import com.feima.baseproject.manager.TaskManager;
import com.feima.baseproject.task.BaseTask;

import butterknife.InjectView;
import butterknife.Optional;


public abstract class BaseFragment extends Fragment {
    public String taskTag = "BaseFragment";//当前Fragment的线程标识
    public TaskManager taskManager = TaskManager.getTaskManagerInstance();
    public MFragmentsManager mFragmentsManager = MFragmentsManager.getFragmentManagerInstance();
    public ScreenManager screenManager = ScreenManager.getScreenManagerInstance();
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    protected boolean isPrepared;
    @Optional
    @InjectView(R.id.base_view_contain_layout)
    public LinearLayout containLayout;
    @Optional
    @InjectView(R.id.base_view_link_load)
    public LinearLayout loadLayout;
    @Optional
    @InjectView(R.id.base_ui_title_title)
    public TextView titleText;
    @Optional
    @InjectView(R.id.base_ui_title_back_layout)
    public LinearLayout backLayout;

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
        cancelTasks();
        super.onDestroy();
    }

    /**
     * 初始化视图相关操作
     */
    public abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

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
     * 关闭当前Fragment中所有还在运行的线程
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