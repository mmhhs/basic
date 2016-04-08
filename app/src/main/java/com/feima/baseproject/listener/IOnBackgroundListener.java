package com.feima.baseproject.listener;


import com.feima.baseproject.task.BaseTask;
import com.feima.baseproject.util.BaseConstant;

public interface IOnBackgroundListener {
    BaseConstant.TaskResult onBackground(BaseTask task);
}