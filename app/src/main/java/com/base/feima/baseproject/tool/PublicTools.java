package com.base.feima.baseproject.tool;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class PublicTools{
	public static String tag = "PublicTools";


    /**
     * ����绰
     * @param context
     */
    public static void call(final Context context,final String tel){
        try {
            Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

		
	
	/**
	 * ����ȫ������
	 * @param activity
	 */
	public static void startFullScreen(Activity activity){
		activity.getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
	              WindowManager.LayoutParams. FLAG_FULLSCREEN);//ȫ��
	}
	
	/**
	 * ȡ��ȫ������
	 * @param activity
	 */
	public static void quitFullScreen(Activity activity){
	      final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
	      attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
	      activity.getWindow().setAttributes(attrs);
	      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}


	
	/**
	 * ���ؼ���
	 * @param activity
	 */
	public static void closeKeyBoard(Activity activity){
		
		try{
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			((InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

    /**
     * �򿪼���
     * @param activity
     */
    public static void openKeyBoard(Handler handler,final Activity activity,int delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, delay);
    }




	
	/**
	 * ��ȡ״̬���߶�
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity){
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	/**
	 * ��ȡ�������߶�
	 * @param activity
	 * @return
	 */
	public static int getTitleBarHeight(Activity activity){
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;		
		int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  
		//statusBarHeight�����������״̬���ĸ߶�  
		int titleBarHeight = contentTop - statusBarHeight;
		return titleBarHeight;
	}
	
	/**
	 * ��ȡ��Ļ���px
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity){		
		int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
		return screenWidth;
	}
	
	/**
	 * ��ȡ��Ļ�߶�px
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity){		
		int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
		return screenHeight;
	}


    /**
     * ��z�ַ��������ļ���
     * @param message
     * @param fileName
     */
	public static void writeFileSdcard(String message, String fileName) { 
		 
        try { 
        	String messages=message+"\n";
        	 File file = new File(fileName);
             if (!file.exists()) {
              file.createNewFile();
             }
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = messages.getBytes();
            fout.write(bytes);
            fout.close();
        } 
 
        catch (Exception e) {
            e.printStackTrace(); 
 
        }  
    } 

	/**
	 * ����չʾͼƬ�߶�
	 * @param width
	 * @param height
	 * @param targetWidth
	 * @return
	 */
	public static int computeHight(String width,String height,int targetWidth,float rate){
		int targetHeight = 0;
		try {
			float w = Float.parseFloat(width);
			float h = Float.parseFloat(height);
			if(w>h){
				targetHeight = (int) (targetWidth*rate);
			}else if(width==height){
				targetHeight = (int) (h*targetWidth/w);
			}else{
				targetHeight = (int) (targetWidth/rate);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return targetHeight;
		}
		
	}
	


	
	/**
	 * ����toast
	 * @param context
	 * @param value
	 */
	public static void addToast(Context context,String value){
		try {			
			Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡϵͳ�汾
	 * @return
	 */
	public static int getSystemVersion(){
		int sysVersion = VERSION.SDK_INT;  
		return sysVersion;
	}


    /**
     * ��ȡ�豸Id���ֻ�Ʒ���ԡ�_���ָ�
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String result = ""+tm.getDeviceId()+"_"+ Build.BRAND;
        return result;
    }

	
	/**
	 * ��ȡ�汾��
	 * @return ��ǰӦ�õİ汾��
	 */
	public static int getVersionCode(Context context) {
		int code = 1;
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        code = info.versionCode;	       
	    } catch (Exception e) {
	        e.printStackTrace();	       
	    }
	    return code;
	}
	
	/**
	 * ��ȡ�汾����
	 * @return ��ǰӦ�õİ汾����
	 */
	public static String getVersionName(Context context) {
		String name = "";
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        name = info.versionName;	       
	    } catch (Exception e) {
	        e.printStackTrace();	       
	    }
	    return name;
	}

    /**
     * ���������ҳ
     * @param context
     * @param url
     */
    public static void openBrowser(Context context,String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * ����ˢ����ɫ
     * @param swipeRefreshLayout
     */
    public static void setSwipeRefreshColor(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    /**
     * ����ˢ��
     * @param swipeRefreshLayout
     */
    public static void setSwipeRefreshFinish(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * ��װ
     *
     * @param context
     *            �����ⲿ��������context
     */
    public static void install(Context context,String storePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(storePath)),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ����apk�ļ��İ汾��
     * @param context
     * @param storePath
     * @return
     */
    public static int getApkVersionCode(Context context,String storePath){
        int versionCode = 1;
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(storePath, PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo = null;
        if (info != null) {
            appInfo = info.applicationInfo;
            versionCode = info.versionCode;
        }
        return versionCode;
    }

}