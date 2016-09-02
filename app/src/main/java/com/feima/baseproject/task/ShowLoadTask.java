package com.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnTryClickListener;
import com.feima.baseproject.task.TaskConstant.TaskResult;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.util.net.HttpUtil;
import com.feima.baseproject.util.net.Httpclient;
import com.feima.baseproject.util.tool.StringUtil;
import com.feima.baseproject.view.dialog.ViewUtil;

import java.util.Map;

public class ShowLoadTask extends DispatchTask {
	public View contentView;//内容界面
	public LinearLayout loadLayout;//加载界面
	public IOnTryClickListener iOnTryClickListener;
	public ViewUtil viewUtil;

	/**
	 * 本地处理耗时线程
	 * @param activity           上下文
	 * @param contentView       内容视图
	 * @param loadLayout          加载视图
	 * @param loadString        显示文字
	 * @param showLoad          是否显示loadview 若显示，则contentView，loadView不能为空
	 */
	public ShowLoadTask(Activity activity, String tagString, View contentView, LinearLayout loadLayout, String loadString, boolean showLoad, IOnTryClickListener iOnTryClickListener){
		this.activity = activity;
		this.tagString = tagString;
		this.contentView = contentView;
		this.loadLayout = loadLayout;
		this.loadString = loadString;
		this.showLoad = showLoad;
		this.iOnTryClickListener = iOnTryClickListener;
		init();
	}

	/**
	 * 网络加载线程
	 * @param activity           上下文
	 * @param contentView       内容视图
	 * @param loadLayout          加载视图
	 * @param loadString        显示文字
	 * @param showLoad          是否显示loadview 若显示，则contentView，loadView不能为空
	 * @param httpUrl           访问路径
	 * @param argMap            参数集合
	 * @param accessType        访问方式
	 */
	public ShowLoadTask(Activity activity, String tagString, View contentView, LinearLayout loadLayout, String loadString, boolean showLoad, IOnTryClickListener iOnTryClickListener, String httpUrl, Map<String, Object> argMap, int accessType){
		this.activity = activity;
		this.tagString = tagString;
		this.contentView = contentView;
		this.loadLayout = loadLayout;
		this.loadString = loadString;
		this.showLoad = showLoad;
		this.iOnTryClickListener = iOnTryClickListener;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		init();
	}

	public void init(){
		super.init();
		viewUtil = new ViewUtil();
		Httpclient.setContext(activity);
		view = contentView;
	}

	@Override
	public void onPreExecute()
	{
		try {
			if(StringUtil.isEmpty(loadString)){
				loadString = activity.getResources().getString(R.string.task2);
			}
			if(!HttpUtil.isnet(activity)){
				netFlag = NET_ERROR;
				if (showLoad) {
					if(contentView==null|| loadLayout ==null){
						return;
					}
					viewUtil.addErrorView(activity, activity.getString(R.string.net_tip),
							contentView, loadLayout, iOnTryClickListener);
				}else {
					OptionUtil.addToast(activity, activity.getString(R.string.net_tip));
				}
			}else {
				if (showLoad) {
					if(contentView==null|| loadLayout ==null){
						return;
					}
					viewUtil.addLoadView(activity, loadString, contentView, loadLayout);
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
			if (netFlag == NET_ERROR) {
				return;
			}
			if(showLoad){
				viewUtil.removeLoadView(contentView, loadLayout);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (iOnResultListener!=null){
			iOnResultListener.onDone(this);
		}
		switch(result){
			case OK:
				if (iOnResultListener!=null){
					iOnResultListener.onOK(this);
				}
				if (!StringUtil.isEmpty(resultMsg)&&showTipSuccess){
					OptionUtil.addToast(activity, resultMsg +"");
				}
				break;
			case ERROR:
				if (showLoad) {
					viewUtil.addErrorView(activity, activity.getString(R.string.nothing),
							contentView, loadLayout, iOnTryClickListener);
				}
				if (iOnResultListener!=null){
					iOnResultListener.onError(this);
				}
				if (!StringUtil.isEmpty(resultMsg)&&showTipError){
					OptionUtil.addToast(activity, resultMsg +"");
				}
				dealLoginInvalid();
				break;
			case CANCELLED:
				if (showLoad) {
					viewUtil.addErrorView(activity, activity.getString(R.string.task1),
							contentView, loadLayout, iOnTryClickListener);
				}
				break;
			default:
				break;
		}
		super.onPostExecute(result);
	}




	/**
	 * 添加空视图
	 * @param str 描述文字
	 * @param imageResourceId 图片资源
     */
	public void addEmptyView(String title, String str, int imageResourceId){
		viewUtil.addEmptyView(activity, title, str, imageResourceId, contentView, loadLayout, iOnTryClickListener);
	}

	public void setiOnTryClickListener(IOnTryClickListener iOnTryClickListener) {
		this.iOnTryClickListener = iOnTryClickListener;
	}


}