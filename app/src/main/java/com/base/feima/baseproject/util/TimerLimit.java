package com.base.feima.baseproject.util;


import com.base.feima.baseproject.listener.IOnTimerListener;

import java.util.Timer;
import java.util.TimerTask;

public class TimerLimit {
    private IOnTimerListener iOnTimerListener;
    private long timeTotal;
    private boolean isFirst = true;
    private static boolean needDay = false;

    public TimerLimit() {
    }

    public Timer startTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (isFirst){
                    isFirst = false;
                }else {
                    timeTotal--;
                }
                long distanceTime = timeTotal;
                String showText = "00:00:00";
                if (distanceTime>=0){
                    showText = dealTime(distanceTime);
                    if (iOnTimerListener!=null){
                        iOnTimerListener.timeOneSecond(""+showText);
                    }
                    if (distanceTime <= 0) {
                        if (iOnTimerListener!=null){
                            iOnTimerListener.timeEnd();
                        }
                    }
                    if (distanceTime == 300) {
                        if (iOnTimerListener!=null){
                            iOnTimerListener.remainFiveMinutes();
                        }
                    }
                }

            }
        };
        Timer confirmTimer = new Timer(true);
        confirmTimer.schedule(task, 10, 1000);
        return confirmTimer;
    }

    /**
     * deal time string
     *
     * @param time
     * @return
     */
    public static String dealTime(long time) {
        StringBuffer returnString = new StringBuffer();
        long day = time / (24 * 60 * 60);
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
        String dayStr = timeStrFormat(String.valueOf(day));
        String hoursStr = timeStrFormat(String.valueOf(hours+(day*24)));
        if (needDay){
            hoursStr = timeStrFormat(String.valueOf(hours));
        }
        String minutesStr = timeStrFormat(String.valueOf(minutes));
        String secondStr = timeStrFormat(String.valueOf(second));
        if (needDay){
            returnString.append(dayStr).append(":").append(hoursStr).append(":").append(minutesStr).append(":").append(secondStr);
        }else {
            returnString.append(hoursStr).append(":").append(minutesStr).append(":").append(secondStr);
        }

        return returnString.toString();
    }

    /**
     * format time
     *
     * @param timeStr
     * @return
     */
    private static String timeStrFormat(String timeStr) {
        switch (timeStr.length()) {
            case 1:
                timeStr = "0" + timeStr;
                break;
        }
        return timeStr;
    }

    public void setiOnTimerListener(IOnTimerListener iOnTimerListener) {
        this.iOnTimerListener = iOnTimerListener;
    }

    public void setTimeTotal(long timeTotal) {
        this.timeTotal = timeTotal/1000;
    }

    public static void setNeedDay(boolean needDay) {
        TimerLimit.needDay = needDay;
    }
}