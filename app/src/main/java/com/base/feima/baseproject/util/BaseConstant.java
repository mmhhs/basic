package com.base.feima.baseproject.util;

import android.content.Context;
import android.os.Environment;


public class BaseConstant
{
	public enum TaskResult 
	{
		OK, 
		ERROR, 
		CANCELLED,
		NETERROR,
		NOTHING
	}
	
	public static String IMAGETAMPPATH = Environment.getExternalStorageDirectory() + "/company/base/cache/tamp/";
	public static String IMAGESAVEPATH = Environment.getExternalStorageDirectory() + "/company/base/cache/image/";
	public static String IMAGETAMPFOLDER = "/company/base/cache/tamp/";
    public static String IMAGESAVEFOLDER = "/company/base/cache/image/";

    public static String getSaveImageFolder(Context context){
        IMAGESAVEPATH = context.getExternalCacheDir()+IMAGESAVEFOLDER;
        return  IMAGESAVEPATH;
    }

    public static String getTampImageFolder(Context context){
        IMAGETAMPPATH = context.getExternalCacheDir()+IMAGETAMPFOLDER;
        return  IMAGETAMPPATH;
    }

    public static void initImagePath(Context context){
        getSaveImageFolder(context);
        getTampImageFolder(context);
    }
	
	public final static int PAGER_START									=  1;
    public final static int PAGER_COUNT									=  15;
	
	public final static int REQUEST_REFRESH									=  9999;
	
	
	public static String INTENT_ID								=  "INTENT_ID";
	public static String INTENT_TYPE								=  "INTENT_TYPE";
	public static String INTENT_CONTENT								=  "INTENT_CONTENT";
	public static String INTENT_CLASS								=  "INTENT_CLASS";
	
	//real
	public final static String SERVICE_HOST_IP_LAN		     = "http://www.meijiab.cn/admin/index.php/Api/";
	public final static String SERVICE_HOST_IP_LAN_IMG	 = "http://www.meijiab.cn/admin/";
	
	//test
//	public final static String SERVICE_HOST_IP_LAN		     = "http://www.meijiab.cn/test/index.php/Api/";
//	public final static String SERVICE_HOST_IP_LAN_IMG	 = "http://www.meijiab.cn/test/";

	public static String VERSION									=  "Version/getVersion";
	public static String LOGIN									=  "User/login";

	
}
