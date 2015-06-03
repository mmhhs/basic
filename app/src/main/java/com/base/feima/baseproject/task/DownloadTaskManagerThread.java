/**    
 * @Title: RequestTaskThread.java  
 * @Package com.kingsoft.filesystem  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author zengzhaoshuai zengzhaoshuai@kingsoft.com    
 * @date 2012-2-25 上午10:35:00  
 * @version V1.0    
 */  
package com.base.feima.baseproject.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadTaskManagerThread implements Runnable {  
  
    private DownloadTaskManager downloadTaskManager;
  
    // 创建一个可重用固定线程数的线程池  
    private ExecutorService pool;  
    // 线程池大小  
    private final int POOL_SIZE = 3;  
    // 轮询时间  
    private final int SLEEP_TIME = 1000;  
    // 是否停止  
    private boolean isStop = false;  
  
    public DownloadTaskManagerThread() {  
        downloadTaskManager = DownloadTaskManager.getInstance();
        pool = Executors.newFixedThreadPool(POOL_SIZE);  
  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        while (!isStop) {  
            DownloadTask downloadTask = downloadTaskManager.getDownloadTask();
            if (downloadTask != null) {  
                pool.execute(downloadTask);  
            } else {  //如果当前未有downloadTask在任务队列中  
                try {  
                    // 查询任务完成失败的,重新加载任务队列  
                    // 轮询,  
                    Thread.sleep(SLEEP_TIME);  
                } catch (InterruptedException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
  
        }  
        if (isStop) {  
            pool.shutdown();  
        }  
  
    }  
  
    /** 
     * @param isStop 
     *            the isStop to set 
     */  
    public void setStop(boolean isStop) {  
        this.isStop = isStop;  
    }  
  
}  