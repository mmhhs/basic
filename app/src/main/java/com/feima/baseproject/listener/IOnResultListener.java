package com.feima.baseproject.listener;


import com.feima.baseproject.task.DispatchTask;

public interface IOnResultListener {
    void onOK(DispatchTask task);
    void onError(DispatchTask task);
    void onDone(DispatchTask task);
}