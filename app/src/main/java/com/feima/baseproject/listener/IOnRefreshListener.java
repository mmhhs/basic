package com.feima.baseproject.listener;
/**
 * 刷新加载回调接口
 *
 * @author chenjing
 *
 */
public interface IOnRefreshListener
{
    /**
     * 刷新操作
     */
    void onRefresh();

    /**
     * 加载操作
     */
    void onLoadMore();
}