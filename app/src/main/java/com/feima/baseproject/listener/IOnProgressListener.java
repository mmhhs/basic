package com.feima.baseproject.listener;

public interface IOnProgressListener {
    public void start();
    public void transferred(String transferedBytes, long totalBytes);
    public void success(String fileStorePath);
    public void error(String tip);
    public void done();
}