package com.feima.baseproject.listener;


import com.feima.baseproject.task.BaseTask;

public interface IOnResultListener {
    void onOK(BaseTask task);
    void onError(BaseTask task);
    void onDone(BaseTask task);
}