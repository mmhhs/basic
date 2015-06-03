package com.base.feima.baseproject.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTools {
	
	
	/**
	 * 获取php服务器需要时间
	 * @return
	 */
	public static String getStamp(){
		long time = System.currentTimeMillis();
		String stamp = ""+time;
		stamp = stamp.substring(0,10);
		return stamp;
	}
	
	/**
	 * 根据格式转换为时间戳
	 * @param format 时间格式 如yyyy-MM-dd HH:mm:ss
	 * @param time 时间
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
	 * 根据格式获取当前时间
	 * @param format 时间格式 如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(String format){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   (""+format);     
		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
		String   str   =   formatter.format(curDate);   
		return str;
	}
	
	/**
	 * 格式PHP服务器时间
	 * @param time  php服务器返回时间缺少三位，需要乘以1000
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
	 * 格式PHP服务器时间
	 * @param time  php服务器返回时间缺少三位，需要乘以1000
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