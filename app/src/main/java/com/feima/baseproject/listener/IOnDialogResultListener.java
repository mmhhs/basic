package com.feima.baseproject.listener;


import com.feima.baseproject.task.ShowDialogTask;

public interface IOnDialogResultListener {
    void onOK(ShowDialogTask showDialogTask);
    void onError(ShowDialogTask showDialogTask);
    void onDone(ShowDialogTask showDialogTask);
}