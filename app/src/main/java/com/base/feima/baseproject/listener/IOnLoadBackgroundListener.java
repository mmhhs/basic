package com.base.feima.baseproject.listener;

import com.base.feima.baseproject.task.ShowLoadTask;
import com.base.feima.baseproject.util.BaseConstant;

public interface IOnLoadBackgroundListener {
    public BaseConstant.TaskResult onBackground(ShowLoadTask showLoadTask);
}