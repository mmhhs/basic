package com.base.feima.baseproject.task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DownloadTaskManager {  
    private static final String TAG="DownloadTaskManager";  
    // UI�������  
    private LinkedList<DownloadTask> downloadTasks;  
    // �������ظ�  
    private Set<String> taskIdSet;  
  
    private static DownloadTaskManager downloadTaskMananger;
  
    private DownloadTaskManager() {  
  
        downloadTasks = new LinkedList<DownloadTask>();  
        taskIdSet = new HashSet<String>();  
          
    }  
  
    public static synchronized DownloadTaskManager getInstance() {
        if (downloadTaskMananger == null) {  
            downloadTaskMananger = new DownloadTaskManager();
        }  
        return downloadTaskMananger;  
    }  
  
    //1.��ִ��  
    public void addDownloadTask(DownloadTask downloadTask) {  
        synchronized (downloadTasks) {  
            if (!isTaskRepeat(downloadTask.getFileId())) {  
                // ������������  
                downloadTasks.addLast(downloadTask);   
            }  
        }  
  
    }  
    public boolean isTaskRepeat(String fileId) {  
        synchronized (taskIdSet) {  
            if (taskIdSet.contains(fileId)) {  
                return true;  
            } else {  
                System.out.println("���ع�����������������"+ fileId);  
                taskIdSet.add(fileId);  
                return false;  
            }  
        }  
    }  
      
    public DownloadTask getDownloadTask() {  
        synchronized (downloadTasks) {  
            if (downloadTasks.size() > 0) {  
                System.out.println("���ع�����������������"+"ȡ������");  
                DownloadTask downloadTask = downloadTasks.removeFirst();  
                return downloadTask;  
            }  
        }  
        return null;  
    }  
}  