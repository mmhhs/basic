package com.base.feima.baseproject.listener;

import com.base.feima.baseproject.task.ShowLoadTask;

public interface IOnLoadResultListener {
    public void onOK(ShowLoadTask showLoadTask);
    public void onError(ShowLoadTask showLoadTask);
    public void onDone(ShowLoadTask showLoadTask);
}