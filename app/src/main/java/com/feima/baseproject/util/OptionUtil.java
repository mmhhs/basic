package com.feima.baseproject.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnDialogListener;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.io.File;
import java.io.FileOutputStream;


public class OptionUtil {
	public static String tag = "OptionUtil";


	/**
	 * 拨打电话
	 * @param context
	 */
	public static void call(final Context context,final String tel){
		try {
			Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel));
			context.startActivity(intent);
		}catch (Exception e){
			e.printStackTrace();
		}
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
	 * 获取设备Id和手机品牌以“_”分隔
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String result = ""+tm.getDeviceId()+"_"+ Build.BRAND;
		return result;
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


	/**
	 * 结束刷新
	 * @param swipeRefreshLayout
	 */
	public static void setSwipeRefreshFinish(SwipeRefreshLayout swipeRefreshLayout){
		swipeRefreshLayout.setRefreshing(false);
	}

	/**
	 * 安装
	 *
	 * @param context
	 *            接收外部传进来的context
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
	 * 获取本地apk文件的版本号
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

	/**
	 * 获取本机号码
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context){
		String result = "";
		try {
			TelephonyManager mTelephonyMgr;
			mTelephonyMgr = (TelephonyManager)  context.getSystemService(Context.TELEPHONY_SERVICE);
			result = mTelephonyMgr.getLine1Number();
			if (result.startsWith(context.getString(R.string.base_86))){
				result = result.replace(context.getString(R.string.base_86),"");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置输入框光标位置最后
	 * @param editText
	 */
	public static void setCursor2Last(EditText editText){
		editText.setSelection(editText.getText().length());
	}

	public static void setWebsettingBase(WebView webView){
		WebSettings webSettings = webView.getSettings();
		//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点
		webView.requestFocusFromTouch();
		webView.requestFocus();
		//打开页面时， 自适应屏幕
		webSettings.setUseWideViewPort(true);//关键点 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
		webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
		//支持通过JS打开新窗口
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		//其他
		webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//支持内容重新布局
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //webview中缓存
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.supportMultipleWindows();  //多窗口
		// 开启 DOM storage API 功能?
		webSettings.setDomStorageEnabled(true);
		//开启 database storage API 功能?
		webSettings.setDatabaseEnabled(true);
		String cacheDirPath = BaseConstant.IMAGETAMPPATH;
		//设置? Application Caches 缓存目录?
		webSettings.setAppCachePath(cacheDirPath);
		//开启 Application Caches 功能?
		webSettings.setAppCacheEnabled(true);
	}

	/**
	 * 设置webview 不支持缩放
	 * @param webView
	 */
	public static void setWebsetting(WebView webView){
		setWebsettingBase(webView);
		WebSettings webSettings = webView.getSettings();
		//页面支持缩放
		webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
		webSettings.setSupportZoom(false); // 支持缩放

	}

	/**
	 * 设置webview 支持缩放
	 * @param webView
	 */
	public static void setWebsetting2(WebView webView){
		setWebsettingBase(webView);
		WebSettings webSettings = webView.getSettings();
		//页面支持缩放
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
		webSettings.setSupportZoom(true); // 支持缩放
	}

	public static boolean isGpsOpen(final Activity activity, View view){
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			DialogUtil dialogUtil = new DialogUtil(activity);
			dialogUtil.setDismissKeyback(true);
			dialogUtil.setDismissOutside(true);
			dialogUtil.setTitle("GPS模块没有打开,是否现在打开？");
			dialogUtil.showTipDialog(view,"");
			dialogUtil.setiOnDialogListener(new IOnDialogListener() {
				@Override
				public void onConfirm() {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					activity.startActivity(intent);
				}

				@Override
				public void onCancel() {

				}

				@Override
				public void onOther() {

				}
			});
		}
		return false;
	}


}