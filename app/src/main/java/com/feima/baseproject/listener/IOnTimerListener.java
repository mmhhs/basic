package com.feima.baseproject.listener;

public interface IOnTimerListener {
    void timeEnd();
    void remainFiveMinutes();
    void timeOneSecond(String time);
}