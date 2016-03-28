package com.feima.baseproject.listener;

public interface IOnLoadListener {
    void onStart();
    void onSuccess();
    void onFailure();
    void onCancel();
}