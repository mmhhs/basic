package com.base.feima.baseproject.task;

/**
 * 进度监听器接口
 */
public interface ProgressListener {
	public void transferred(long transferedBytes);
}
