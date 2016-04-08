package com.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

import com.feima.baseproject.R;
import com.feima.baseproject.task.TaskConstant.TaskResult;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.util.net.HttpUtil;
import com.feima.baseproject.util.net.Httpclient;
import com.feima.baseproject.util.tool.StringUtil;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.io.File;
import java.util.List;
import java.util.Map;


public class ShowDialogTask extends DispatchTask{
	public DialogUtil dialogUtil;
	public View parentView;//父类视图
	public boolean showNetToast = false;//显示网络问题toast
	public PopupWindow loadPopupWindow;//加载框

	/**
	 * 本地处理耗时线程
	 * @param activity 上下文
	 * @param parentView 父类视图 
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示，则parentView不能为空
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showLoad){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.tagString = tagString;
		init();
	}

	/**
	 * 网络加载线程
	 * @param activity 上下文
	 * @param parentView 父类视图
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity, String tagString, View parentView, String loadsString, boolean showLoad, String httpUrl, Map<String, Object> argMap, int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		this.tagString = tagString;
		init();
	}
	/**
	 * 网络加载线程 最后一个参数若为true，则没有网络时，弹出提示
	 * @param activity 上下文
	 * @param parentView 父类视图
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity, String tagString, View parentView, String loadsString, boolean showLoad, String httpUrl, Map<String, Object> argMap, int accessType, boolean showNet){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		this.tagString = tagString;
		this.showNetToast = showNet;
		init();
	}
	/**
	 * 网络加载线程-上传文件
	 * @param activity 上下文
	 * @param parentView 父类视图 
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity, String tagString, View parentView, String loadsString, boolean showLoad, String httpUrl, Map<String, Object> argMap, List<File> fileList, int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.accessType = accessType;
		this.tagString = tagString;
		init();
	}
	/**
	 * 网络加载线程-上传文件-文件标识
	 * @param activity 上下文
	 * @param parentView 父类视图 
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param key 服务器判断文件标识
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity, String tagString, View parentView, String loadsString, boolean showLoad, String httpUrl, Map<String, Object> argMap, List<File> fileList, String key, int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.keyString = key;
		this.accessType = accessType;
		this.tagString = tagString;
		init();
	}
	/**
	 * 网络加载线程-上传文件-文件标识 最后一个参数若为true，则没有网络时，弹出提示
	 * @param activity 上下文
	 * @param parentView 父类视图
	 * @param loadsString 显示文字
	 * @param showLoad 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param key 服务器判断文件标识
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity, String tagString, View parentView, String loadsString, boolean showLoad, String httpUrl, Map<String, Object> argMap, List<File> fileList, String key, int accessType, boolean showNet){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showLoad = showLoad;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.keyString = key;
		this.accessType = accessType;
		this.showNetToast = showNet;
		this.tagString = tagString;
		init();
	}

	public void init(){
		super.init();
		dialogUtil = new DialogUtil(activity);
		Httpclient.setContext(activity);
	}

	@Override
	public void onPreExecute()
	{
		try {
			super.onPreExecute();
			if(StringUtil.isEmpty(loadString)){
				loadString = activity.getResources().getString(R.string.task3);
			}
			if(!HttpUtil.isnet(activity)){
				netFlag = NET_ERROR;
				if (showNetToast) {
					OptionUtil.addToast(activity, activity.getResources().getString(R.string.net_tip));
				}
			}else {
				if (showLoad) {
					if(parentView==null){
						return;
					}
					loadPopupWindow = dialogUtil.showLoadDialog(parentView, loadString);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPostExecute(TaskResult result)
	{
		try {
			if (loadPopupWindow!=null) {
				loadPopupWindow.dismiss();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(iOnResultListener !=null){
			this.iOnResultListener.onDone(this);
		}
		switch(result){
			case OK:
				if(iOnResultListener !=null){
					this.iOnResultListener.onOK(this);
				}
				if (!StringUtil.isEmpty(resultMsg)&&showTipSuccess){
					OptionUtil.addToast(activity, resultMsg +"");
				}
				break;
			case ERROR:
				if(iOnResultListener !=null){
					this.iOnResultListener.onError(this);
				}
				if (!StringUtil.isEmpty(resultMsg)&&showTipError){
					OptionUtil.addToast(activity, resultMsg +"");
				}
				dealLoginInvalid();
				break;
			case CANCELLED:
				OptionUtil.addToast(activity, activity.getString(R.string.task1));
				break;
			default:
				break;
		}
		super.onPostExecute(result);
	}



}