package com.feima.baseproject.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtil{
	public final static String USER_ID = "USER_ID";

	//经纬度
	public final static String LOCATION = "LOCATION";
	public final static String LATITUDE = "LATITUDE";
	public final static String LONGITUDE = "LONGITUDE";

	public final static String HELP = "HELP";
	public final static String IGNOREVERSION = "IGNOREVERSION";//忽略版本
	public final static String ADIMAGEPATH = "ADIMAGEPATH";
	public final static String MIUI = "MIUI";

	/**
	 * 保存用户id
	 * @param context
	 * @param id 用户id
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
		
		SharedPreferences sp = context.getSharedPreferences(USER_ID, 0);
		String id = sp.getString(USER_ID, "");
		return id;
	}

	/**
	 * 保存地理位置信息
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
	 * 获取纬度
	 * @param context
	 * @return
	 */
	public static String getLat(Context context){
		
		SharedPreferences sp = context.getSharedPreferences(LOCATION, 0);
		String lat = sp.getString(LATITUDE, "");
		return lat;
	}
	/**
	 * 获取经度
	 * @param context
	 * @return
	 */
	public static String getLng(Context context){
		
		SharedPreferences sp = context.getSharedPreferences(LOCATION, 0);
		String lng = sp.getString(LONGITUDE, "");
		return lng;
	}

	/**
	 * 保存帮助界面显示状态
	 * @param context
	 * @param status true表示需要显示
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
		
		SharedPreferences sp = context.getSharedPreferences(HELP, 0);
		boolean status = sp.getBoolean(HELP, true);
		return status;
	}
	public static int getHelpCode(Context context){
		
		SharedPreferences sp = context.getSharedPreferences(HELP, 0);
		int code = sp.getInt("code", 1);
		return code;
	}

	/**
	 * 保存忽略版本
	 * @param context
	 * @param version 忽略版本
	 */
	public static void saveIgnoreVersion(Context context, String version)
	{
		SharedPreferences sp = context.getSharedPreferences(IGNOREVERSION, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.putString(IGNOREVERSION, version);
		editor.commit();
	}
	public static String getIgnoreVersion(Context context){
		
		SharedPreferences sp = context.getSharedPreferences(IGNOREVERSION, 0);
		String id = sp.getString(IGNOREVERSION, "");
		return id;
	}

	/**
	 * 设置页面引导已知道
	 * @param context
	 * @param className
	 * @param first
	 */
	public static void saveFisrt(Context context, String className, boolean first)
	{
		SharedPreferences sp = context.getSharedPreferences(className, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.putBoolean("first", first);
		editor.commit();
	}
	public static boolean getFirst(Context context,String className){
		
		SharedPreferences sp = context.getSharedPreferences(className, 0);
		boolean bool = sp.getBoolean("first", false);
		return bool;
	}

	/**
	 * 保存广告图片路径
	 * @param context
	 * @param url
	 */
	public static void saveAdImage(Context context, String url)
	{
		SharedPreferences sp = context.getSharedPreferences(ADIMAGEPATH, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.putString(ADIMAGEPATH, url);
		editor.commit();
	}
	public static String getAdImage(Context context){
		
		SharedPreferences sp = context.getSharedPreferences(ADIMAGEPATH, 0);
		String id = sp.getString(ADIMAGEPATH, "");
		return id;
	}

	/**
	 * 保存是否是miui系统
	 * @param context
	 * @param isMIUI
	 */
	public static void saveMIUI(Context context, boolean isMIUI, boolean isChecked)
	{
		SharedPreferences sp = context.getSharedPreferences(MIUI, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.putBoolean(MIUI, isMIUI);
		editor.putBoolean("isChecked", isChecked);
		editor.commit();
	}
	public static boolean getMIUI(Context context){
		SharedPreferences sp = context.getSharedPreferences(MIUI, 0);
		boolean isMIUI = sp.getBoolean(MIUI, false);
		return isMIUI;
	}

	public static boolean getMIUIChecked(Context context){
		SharedPreferences sp = context.getSharedPreferences("isChecked", 0);
		boolean isChecked = sp.getBoolean("isChecked", false);
		return isChecked;
	}

}