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
		mBuilder.setContentTitle(context.getResources().getString(R.string.app_name))//����֪ͨ������
		.setVibrate(vibreate)
		.setOnlyAlertOnce(false)
		.setContentText(content) //<span style="font-family: Arial;">/����֪ͨ����ʾ����</span>
		.setContentIntent(getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL,intent)) //����֪ͨ�������ͼ
//		.setNumber(number) //����֪ͨ���ϵ�����
		.setTicker(content) //֪ͨ�״γ�����֪ͨ��������������Ч����
		.setWhen(System.currentTimeMillis())//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ��һ����ϵͳ��ȡ����ʱ��
		.setPriority(Notification.PRIORITY_HIGH) //���ø�֪ͨ���ȼ�
		.setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��  
		.setOngoing(false)//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
		.setDefaults(Notification.DEFAULT_ALL)//��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ��������
		//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND ������� // requires VIBRATE permission
		.setSmallIcon(R.drawable.ic_launcher);//����֪ͨСICON
		
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