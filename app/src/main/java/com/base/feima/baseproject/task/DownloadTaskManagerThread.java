/**    
 * @Title: RequestTaskThread.java  
 * @Package com.kingsoft.filesystem  
 * @Description: TODO(��һ�仰�������ļ���ʲô)  
 * @author zengzhaoshuai zengzhaoshuai@kingsoft.com    
 * @date 2012-2-25 ����10:35:00  
 * @version V1.0    
 */  
package com.base.feima.baseproject.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadTaskManagerThread implements Runnable {  
  
    private DownloadTaskManager downloadTaskManager;
  
    // ����һ�������ù̶��߳������̳߳�  
    private ExecutorService pool;  
    // �̳߳ش�С  
    private final int POOL_SIZE = 3;  
    // ��ѯʱ��  
    private final int SLEEP_TIME = 1000;  
    // �Ƿ�ֹͣ  
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
            } else {  //�����ǰδ��downloadTask�����������  
                try {  
                    // ��ѯ�������ʧ�ܵ�,���¼����������  
                    // ��ѯ,  
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