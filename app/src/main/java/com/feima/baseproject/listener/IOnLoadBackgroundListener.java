package com.feima.baseproject.listener;


import com.feima.baseproject.task.ShowLoadTask;
import com.feima.baseproject.util.BaseConstant;

public interface IOnLoadBackgroundListener {
    BaseConstant.TaskResult onBackground(ShowLoadTask showLoadTask);
}