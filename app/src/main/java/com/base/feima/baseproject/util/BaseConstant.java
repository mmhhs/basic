package com.base.feima.baseproject.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;


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
	
	public static String IMAGETAMPPATH = Environment.getExternalStorageDirectory() + "/company/tamp/";
	public static String IMAGESAVEPATH = Environment.getExternalStorageDirectory() + "/company/";
	public static String IMAGETAMPFOLDER = "/company/tamp/";
    public static String IMAGESAVEFOLDER = "/company/";
    public static String CACHENAME = "image";
    public static String APKPATH = "/supertoys.apk";

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
        APKPATH = IMAGESAVEPATH+APKPATH;
        File saveFile = new File(IMAGESAVEPATH);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        File tampFile = new File(IMAGETAMPPATH);
        if (!tampFile.exists()){
            tampFile.mkdirs();
        }
    }

    public static final int SCALE_WIDTH                                 = 360;
    public static final int SCALE_HEIGHT                                = 640;
	public final static int PAGER_START									=  1;
    public final static int PAGER_COUNT									=  15;
	
	public final static int REQUEST_REFRESH									=  9999;
	
	
	public static String INTENT_ID								=  "INTENT_ID";
	public static String INTENT_TYPE								=  "INTENT_TYPE";
	public static String INTENT_CONTENT								=  "INTENT_CONTENT";
	public static String INTENT_CLASS								=  "INTENT_CLASS";

    //Extranet
    public final static String SERVICE_HOST_IP	 = "http://app.supertoys.com.cn:8080";
    //Intranet
//    public final static String SERVICE_HOST_IP	 = "http://192.168.1.115:8080";

    public final static String SERVICE_HOST_IP_LAN		     = SERVICE_HOST_IP+"/appapi/";
    public final static String SERVICE_HOST_IP_LAN_IMG	     = SERVICE_HOST_IP;
    public final static String SERVICE_HOST_IP_LANS		     = SERVICE_HOST_IP+"/appsapi/";

	public static String VERSION									=  SERVICE_HOST_IP_LAN+"Version/getVersion";
	public static String LOGIN									    =  SERVICE_HOST_IP_LAN+"User/login";

	
}
