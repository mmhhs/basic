package com.base.feima.baseproject.listener;

import com.base.feima.baseproject.task.ShowLoadTask;

public interface IOnLoadResultListener {
    void onOK(ShowLoadTask showLoadTask);
    void onError(ShowLoadTask showLoadTask);
    void onDone(ShowLoadTask showLoadTask);
}