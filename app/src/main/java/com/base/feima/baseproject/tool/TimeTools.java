package com.base.feima.baseproject.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTools {
	
	
	/**
	 * ��ȡphp��������Ҫʱ��
	 * @return
	 */
	public static String getStamp(){
		long time = System.currentTimeMillis();
		String stamp = ""+time;
		stamp = stamp.substring(0,10);
		return stamp;
	}
	
	/**
	 * ���ݸ�ʽת��Ϊʱ���
	 * @param format ʱ���ʽ ��yyyy-MM-dd HH:mm:ss
	 * @param time ʱ��
	 * @return
	 */
	public static String tranFormatTime(String time,String format){
		String re_time = "";		
		try {
			SimpleDateFormat   formatter   =   new   SimpleDateFormat   (""+format);     		
			Date date = formatter.parse(time);
			long l = date.getTime();
			re_time = String.valueOf(l);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return re_time;
	}

	
	/**
	 * ���ݸ�ʽ��ȡ��ǰʱ��
	 * @param format ʱ���ʽ ��yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(String format){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   (""+format);     
		Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
		String   str   =   formatter.format(curDate);   
		return str;
	}
	
	/**
	 * ��ʽPHP������ʱ��
	 * @param time  php����������ʱ��ȱ����λ����Ҫ����1000
	 * @return
	 */
	public static String formatServiceTime1(String time) {
        String result = "";
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	 long loc_time = Long.valueOf(time);
        	 result = sdf.format(new Date(loc_time*1000));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}        
	}
	/**
	 * ��ʽPHP������ʱ��
	 * @param time  php����������ʱ��ȱ����λ����Ҫ����1000
	 * @return
	 */
	public static String formatServiceTime2(String time) {
        String result = "";
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	 long loc_time = Long.valueOf(time);
        	 result = sdf.format(new Date(loc_time*1000));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}        
	}

	
}