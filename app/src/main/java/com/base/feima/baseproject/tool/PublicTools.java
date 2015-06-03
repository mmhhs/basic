package com.base.feima.baseproject.tool;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool.OnSureClickListener;
import com.base.feima.baseproject.util.BaseConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class PublicTools{
	public static String tag = "PublicTools";


    /**
     * 电话对话框
     * @param context
     */
    public static void showPhoneWindow(final Context context,final String tel,View view){
        PopupwindowTool popupwindowTool = new PopupwindowTool();
        popupwindowTool.setOnSureClickListener(new OnSureClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));
                context.startActivity(intent);
            }
        });
        popupwindowTool.showSureWindow(context,view,"",context.getResources().getString(R.string.dialog_item4),true,true,false,0);
    }
	

	/**
	 * 显示未登录提示
	 * @param activity
	 * @param view
	 */
	public static void showLoginPopupwindow(final Activity activity,View view){
		PopupwindowTool popupwindowTool = PopupwindowTool.getInstancePopupwindowTool();
		popupwindowTool.showSureWindow(activity, view, activity.getResources().getString(R.string.dialog_item0), activity.getResources().getString(R.string.dialog_item5),
				true, true, false, R.style.base_anim_alpha);
		popupwindowTool.setOnSureClickListener(new OnSureClickListener(){

			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
		
		
	
	/**
	 * 开启全屏设置
	 * @param activity
	 */
	public static void startFullScreen(Activity activity){
		activity.getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
	              WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
	}
	
	/**
	 * 取消全屏设置
	 * @param activity
	 */
	public static void quitFullScreen(Activity activity){
	      final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
	      attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
	      activity.getWindow().setAttributes(attrs);
	      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	/**
	 * 判断网络返回结果
	 * @param result
	 * @return
	 */
	public static boolean judgeResult(Context context, String result){
		String[] codeArray = context.getResources().getStringArray(R.array.base_error_key);
		if(result.equals(codeArray[0])||result.equals(codeArray[1])){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断网络返回结果
	 * @param result
	 * @return
	 */
	public static String judgeResult2(Context context, String result){
		String msg = "";
		try {
			String[] codeArray = context.getResources().getStringArray(R.array.base_error_key);
			String[] errorArray = context.getResources().getStringArray(R.array.base_error_vaule);
			for(int i=0;i<codeArray.length;i++){
				if(result.equals(codeArray[i])){
					msg = errorArray[i];
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return msg;
		}
		
		
	}	
	
	/**
	 * 隐藏键盘
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
     * 打开键盘
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
     * 保留digit位小数 方式1
     * @param num 目标数字
     * @param digit 几位小数
     * @return
     */
	public static double storePoint(double num,int digit){
		double result = 0;
		try{
            BigDecimal bd = new BigDecimal(num);
            bd = bd.setScale(digit,BigDecimal.ROUND_HALF_UP);
			result = bd.doubleValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
		}
	}

    /**
     * 保留digit位小数 方式1
     * @param num 目标数字
     * @param digit 几位小数
     * @return
     */
    public static String storePointString(double num,int digit){
        String result = "";
        try{
            BigDecimal bd = new BigDecimal(num);
            bd = bd.setScale(digit,BigDecimal.ROUND_HALF_UP);
            result = String.valueOf(bd.doubleValue());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }
    }
	
	/**
	 * 保留2位小数 方式2
	 * @param num
	 * @return
	 */
	public static String storePoint2(double num){
		String result = "";
		try{
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
			result = df.format(num);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}

    /**
     * 保留小数 方式3
     * @param num
     * @param digit
     * @return
     */
    public static String storePoint3(double num,int digit){
        String result = "";
        try{
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(digit);
            result = nf.format(num);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }

    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     */
    public static String formatMoney(String money){
        String result = "";
        try {
            String[] strs = money.split("\\.");
            String reverseStr = new StringBuilder(strs[0]).reverse().toString();
            String strTemp = "";
            for (int i=0; i<reverseStr.length(); i++) {
                if (i*3+3 > reverseStr.length()){
                    strTemp += reverseStr.substring(i*3,reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i*3, i*3+3)+",";
            }
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length()-1);
            }
            result = new StringBuilder(strTemp).reverse().toString();
            if (strs.length>1){
                result+= "."+strs[1];
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }
	
	/**
	 * 获取状态栏高度
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
	 * 获取标题栏高度
	 * @param activity
	 * @return
	 */
	public static int getTitleBarHeight(Activity activity){
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;		
		int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  
		//statusBarHeight是上面所求的状态栏的高度  
		int titleBarHeight = contentTop - statusBarHeight;
		return titleBarHeight;
	}
	
	/**
	 * 获取屏幕宽度px
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity){		
		int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
		return screenWidth;
	}
	
	/**
	 * 获取屏幕高度px
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity){		
		int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
		return screenHeight;
	}


    /**
     * 将z字符串存在文件里
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
	 * 计算展示图片高度
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
	 * 判断百度地图是否定位成功
	 * @param longtitude
	 * @return
	 */
	public static boolean judgeLocation(double longtitude){
		boolean result = false;
		try {
			String lon = String.valueOf(longtitude);
			if(lon.contains("e")||lon.contains("E")){
				result=false;
			}else{
				result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
//	/**
//	 * 百度地图计算距离--若要使用，需要导入百度地图相关jar包
//	 * @param context
//	 * @param targetLat
//	 * @param targetLon
//	 * @return
//	 */
//	public static String caculateDistance(Context context,String targetLat,String targetLon){
//		String result = "0.01";
//		try {
//			//我的纬度
//			double latF = Double.parseDouble(SharedUtil.getLat(context));
//			//我的经度
//			double lonF = Double.parseDouble(SharedUtil.getLng(context));
//			double lat = Double.parseDouble(targetLat);
//			double lon = Double.parseDouble(targetLon);
//			LatLng p1 = new LatLng(latF,lonF);
//			LatLng p2 = new LatLng(lat,lon);
//			double distance = DistanceUtil.getDistance(p1, p2);
//			result = storeTwoPosition(distance/1000);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally{
//			return ""+result;
//		}
//		
//	}

	
	/**
	 * 弹出toast
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
	 * 获取系统版本
	 * @return
	 */
	public static int getSystemVersion(){
		int sysVersion = VERSION.SDK_INT;  
		return sysVersion;
	}
	

	
	/**
	 * 获取头像正确路径
	 * @return
	 */
	public static String judgeImageUrl(String head) {
		String resultString = "";
		try {
			if (head.startsWith("http")) {
				resultString = head;
			} else {
				resultString = BaseConstant.SERVICE_HOST_IP_LAN_IMG+head;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return resultString;
		}
	}
	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
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
	 * 获取版本名称
	 * @return 当前应用的版本名称
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
     * 浏览器打开网页
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

}