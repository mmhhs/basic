package com.base.feima.baseproject.listener;

import com.base.feima.baseproject.task.ShowDialogTask;
import com.base.feima.baseproject.util.BaseConstant;

public interface IOnDialogBackgroundListener {
    public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask);
}