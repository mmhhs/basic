package com.base.feima.baseproject.util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.base.feima.baseproject.R;

public class NotificationUtil{
	
	
	public static void notice(Context context,String content,Intent intent) {
		long[] vibreate= new long[]{1000,1000,1000,1000};  
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle(context.getResources().getString(R.string.app_name))//设置通知栏标题
		.setVibrate(vibreate)
		.setOnlyAlertOnce(false)
		.setContentText(content) //<span style="font-family: Arial;">/设置通知栏显示内容</span>
		.setContentIntent(getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL,intent)) //设置通知栏点击意图
//		.setNumber(number) //设置通知集合的数量
		.setTicker(content) //通知首次出现在通知栏，带上升动画效果的
		.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
		.setPriority(Notification.PRIORITY_HIGH) //设置该通知优先级
		.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
		.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
		.setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
		//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
		.setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
		
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static PendingIntent getDefalutIntent(Context context,int flags,Intent intent){  
	    PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, intent, flags);  
	    return pendingIntent;  
	}
	
	public static void cancelAllNotification(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
	}
	
}