package com.base.feima.baseproject.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtil{
	public final static String USER_ID = "USER_ID";
	
	//��γ��
	public final static String LOCATION = "LOCATION";
	public final static String LATITUDE = "LATITUDE";
	public final static String LONGITUDE = "LONGITUDE";
	
	public final static String HELP = "HELP";
	
	/**
	 * �����û�id
	 * @param context
	 * @param id �û�id
	 */
	public static void saveUserId(Context context, String id)
	{	      
		  SharedPreferences sp = context.getSharedPreferences(USER_ID, 0); 		  
	      SharedPreferences.Editor editor = sp.edit();
	      editor.clear();
	      editor.putString(USER_ID, id);
	      editor.commit();
	 }	
	public static String getUserId(Context context){
		Context ctx = context;
		SharedPreferences sp = ctx.getSharedPreferences(USER_ID, 0);
		String id = sp.getString(USER_ID, "");		
		return id;
	}
	
	/**
	 * �������λ����Ϣ
	 * @param context
	 * @param lat
	 * @param lng
	 */
	public static void saveLocation(Context context, String lat,String lng)
	{	      
		  SharedPreferences sp = context.getSharedPreferences(LOCATION, 0); 		  
	      SharedPreferences.Editor editor = sp.edit();
	      editor.clear();
	      editor.putString(LATITUDE, lat);
	      editor.putString(LONGITUDE, lng);
	      editor.commit();
	 }	
	/**
	 * ��ȡγ��
	 * @param context
	 * @return
	 */
	public static String getLat(Context context){
		Context ctx = context;
		SharedPreferences sp = ctx.getSharedPreferences(LOCATION, 0);
		String lat = sp.getString(LATITUDE, "");		
		return lat;
	}
	/**
	 * ��ȡ����
	 * @param context
	 * @return
	 */
	public static String getLng(Context context){
		Context ctx = context;
		SharedPreferences sp = ctx.getSharedPreferences(LOCATION, 0);
		String lng = sp.getString(LONGITUDE, "");		
		return lng;
	}
	
	/**
	 * �������������ʾ״̬
	 * @param context
	 * @param status true��ʾ��Ҫ��ʾ
	 */
	public static void saveHelpStatus(Context context, boolean status,int code)
	{	      
		  SharedPreferences sp = context.getSharedPreferences(HELP, 0); 		  
	      SharedPreferences.Editor editor = sp.edit();
	      editor.clear();
	      editor.putBoolean(HELP, status);
	      editor.putInt("code", code);
	      editor.commit();
	 }	
	public static boolean getHelpStatus(Context context){
		Context ctx = context;
		SharedPreferences sp = ctx.getSharedPreferences(HELP, 0);
		boolean status = sp.getBoolean(HELP, true);	
		return status;
	}
	public static int getHelpCode(Context context){
		Context ctx = context;
		SharedPreferences sp = ctx.getSharedPreferences(HELP, 0);
		int code = sp.getInt("code", 1);
		return code;
	}
}