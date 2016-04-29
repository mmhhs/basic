package com.feima.baseproject.listener;

import permissions.dispatcher.PermissionRequest;

public interface IOnPermissionListener {
    /**
     * 执行需要权限的方法
     */
    void doACacheNeedsPermission();
    /**
     * 申请权限
     */
    void ACacheShowRationale(PermissionRequest request);
    /**
     * 拒绝权限执行的方法
     */
    void ACacheOnPermissionDenied();
    /**
     * 不再询问权限执行的方法
     */
    void ACacheOnNeverAskAgain();
}