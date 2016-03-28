package com.feima.baseproject.listener;


import com.feima.baseproject.task.ShowDialogTask;
import com.feima.baseproject.util.BaseConstant;

public interface IOnDialogBackgroundListener {
    BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask);
}