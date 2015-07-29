package com.base.feima.baseproject.listener;

import com.base.feima.baseproject.task.ShowDialogTask;

public interface IOnDialogResultListener {
    public void onOK(ShowDialogTask showDialogTask);
    public void onError(ShowDialogTask showDialogTask);
    public void onDone(ShowDialogTask showDialogTask);
}