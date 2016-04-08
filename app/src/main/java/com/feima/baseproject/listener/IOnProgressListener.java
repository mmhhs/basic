package com.feima.baseproject.listener;

public interface IOnProgressListener {
    void start();
    void transferred(String transferedBytes, long totalBytes);
    void success(String fileStorePath);
    void error(String tip);
    void done();
}