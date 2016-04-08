package com.feima.baseproject.listener;


import com.feima.baseproject.task.DispatchTask;
import com.feima.baseproject.task.TaskConstant;

public interface IOnBackgroundListener {
    TaskConstant.TaskResult onBackground(DispatchTask task);
}