package com.base.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.listener.IOnDialogResultListener;
import com.base.feima.baseproject.listener.IOnEncryptListener;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.model.ResultEntity;
import com.base.feima.baseproject.util.OptionUtil;
import com.base.feima.baseproject.util.ResultUtil;
import com.base.feima.baseproject.util.popupwindow.PopupwindowUtil;
import com.base.feima.baseproject.util.BaseConstant.TaskResult;
import com.base.feima.baseproject.util.tool.JacksonUtil;
import com.base.feima.baseproject.util.tool.StringUtil;
import com.base.feima.baseproject.util.net.HttpUtil;
import com.base.feima.baseproject.util.net.Httpclient;

import java.io.File;
import java.util.List;
import java.util.Map;


public class ShowDialogTask extends BaseTask<Void, String, TaskResult>{
	//显示相关
	public final static int NET_ERROR = 6;//没有网络
	public int netFlag =0;//网络标识
	public Activity activity;//上下文
	public View parentView;//父类视图
	public boolean showDialog = false;//显示加载框
	public boolean showNetToast = false;//显示网络问题toast
	public PopupWindow loadPopupWindow;//加载框
	public String loadString = "";//加载文字
	//访问相关
	public int accessType;//访问方式
	public String httpUrl = "";//网络路径
	public Map<String, Object> argMap;//参数
	public List<File> fileList;//上传文件列表
	public String keyString = "Filedata";//上传文件键值
	//返回值相关
	public String errorMsg;//错误信息
	public String resultsString = null;//返回值
	public boolean loginInvalid = false;//登录失效

	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="ShowDialogTask";
	private IOnDialogResultListener iOnDialogResultListener;
	private IOnDialogBackgroundListener iOnDialogBackgroundListener;
	private IOnEncryptListener iOnEncryptListener;
	private PopupwindowUtil popupwindowTool;

	/**
	 * 本地处理耗时线程
	 * @param activity 上下文
	 * @param parentView 父类视图 
	 * @param loadsString 显示文字
	 * @param showDialog 是否显示dialog 若显示，则parentView不能为空
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
		this.tagString = tagString;
		init();
	}

	/**
	 * 网络加载线程
	 * @param activity 上下文
	 * @param parentView 父类视图
	 * @param loadsString 显示文字
	 * @param showDialog 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
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
	 * @param showDialog 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,int accessType,boolean showNet){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
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
	 * @param showDialog 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
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
	 * @param showDialog 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param key 服务器判断文件标识
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,String key,int accessType){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
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
	 * @param showDialog 是否显示dialog 若显示parentView不能为空
	 * @param httpUrl 访问路径
	 * @param argMap 参数集合
	 * @param fileList 文件集合
	 * @param key 服务器判断文件标识
	 * @param accessType 访问方式
	 */
	public ShowDialogTask(Activity activity,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,String key,int accessType,boolean showNet){
		this.activity = activity;
		this.parentView = parentView;
		this.loadString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.keyString = key;
		this.accessType = accessType;
		this.showNetToast = showNet;
		this.tagString = tagString;
		init();
	}

	private void init(){
		popupwindowTool = new PopupwindowUtil(activity);
		Httpclient.setContext(activity);
	}

	@Override
	public void onPreExecute()
	{
		try {
			addTask();
			if(StringUtil.isEmpty(loadString)){
				loadString = activity.getResources().getString(R.string.task_item3);
			}
			if(!HttpUtil.isnet(activity)){
				netFlag = NET_ERROR;
				if (showNetToast) {
					OptionUtil.addToast(activity, activity.getResources().getString(R.string.net_tip));
				}
			}else {
				if (showDialog) {
					if(parentView==null){
						return;
					}
					loadPopupWindow = popupwindowTool.showLoadWindow(activity, parentView, loadString,0, true,true);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public TaskResult doInBackground(Void... params) {
		// TODO Auto-generated method stub
		TaskResult taskResult = TaskResult.NOTHING;
		//不访问网络的情况
		if(StringUtil.isEmpty(httpUrl)){
			taskResult = doOnBackgroundListener(this);
			return taskResult;
		}
		if (netFlag == NET_ERROR) {
			return taskResult;
		}
		if (iOnEncryptListener!=null){
			iOnEncryptListener.onEncrypt(argMap);
		}
		switch (accessType) {
			case TaskConstant.POST:
				try {
					resultsString = Httpclient.POSTMethod(httpUrl, argMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TaskConstant.PUT:
				try {
					resultsString = Httpclient.PUTMethod(httpUrl, argMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TaskConstant.GET:
				try {
					resultsString = Httpclient.GETMethod(httpUrl, argMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TaskConstant.UPLOAD:
				try {
					resultsString = Httpclient.uploadSubmitFile(httpUrl, argMap, fileList.get(0), keyString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TaskConstant.UPLOADS:
				try {
					resultsString = Httpclient.uploadSubmitFiles(httpUrl, argMap, fileList, keyString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		if(StringUtil.isEmpty(resultsString)){
			taskResult = TaskResult.CANCELLED;
		}else{
			if (iOnDialogBackgroundListener==null) {
				iOnDialogBackgroundListener = defaultBackgroundListener;
			}
			taskResult = doOnBackgroundListener(this);
		}

		return taskResult;
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
		if(iOnDialogResultListener!=null){
			this.iOnDialogResultListener.onDone(this);
		}
		switch(result){
			case OK:
				if(iOnDialogResultListener!=null){
					this.iOnDialogResultListener.onOK(this);
				}
				break;
			case ERROR:
				if(iOnDialogResultListener!=null){
					this.iOnDialogResultListener.onError(this);
				}
				if (!StringUtil.isEmpty(httpUrl)){
					OptionUtil.addToast(activity, "" + errorMsg);
				}
				dealLoginInvalid();
				break;
			case CANCELLED:
				OptionUtil.addToast(activity, activity.getString(R.string.task_item1));
				break;
			default:
				break;
		}
		cancelTask();
	}

	public void addTask(){
		taskManager.addTask(tagString, this);
	}

	public void cancelTask(){
		taskManager.cancelOneTasks(this);
	}

	public void setiOnDialogResultListener(IOnDialogResultListener iOnDialogResultListener) {
		this.iOnDialogResultListener = iOnDialogResultListener;
	}

	public void setiOnDialogBackgroundListener(IOnDialogBackgroundListener iOnDialogBackgroundListener) {
		this.iOnDialogBackgroundListener = iOnDialogBackgroundListener;
	}

	public void setiOnEncryptListener(IOnEncryptListener iOnEncryptListener) {
		this.iOnEncryptListener = iOnEncryptListener;
	}

	private TaskResult doOnBackgroundListener(ShowDialogTask showDialogTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(iOnDialogBackgroundListener!=null){
			taskResult = this.iOnDialogBackgroundListener.onBackground(showDialogTask);
		}
		return taskResult;
	}


	/**
	 * 默认后台解析返回结果
	 */
	private IOnDialogBackgroundListener defaultBackgroundListener = new IOnDialogBackgroundListener(){

		@Override
		public TaskResult onBackground(ShowDialogTask showDialogTask) {
			// TODO Auto-generated method stub
			TaskResult taskResult = TaskResult.NOTHING;
			JacksonUtil json = JacksonUtil.getInstance();
			ResultEntity res = json.readValue(resultsString, ResultEntity.class);
			if(res!=null){
				errorMsg = res.getMsg();
				if(ResultUtil.judgeResult(activity, "" + res.getCode())){
					taskResult = TaskResult.OK;
				}else{
					taskResult = TaskResult.ERROR;
					judgeLoginInvalid(res.getCode());
				}
			}else{
				taskResult = TaskResult.CANCELLED;
			}
			return taskResult;
		}

	};


	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getResultsString() {
		return resultsString;
	}

	/**
	 * 判断登录失效
	 */
	public void judgeLoginInvalid(String code){
		if (ResultUtil.judgeLoginInvalid(activity, "" + code)){
			loginInvalid = true;
		}else {
			loginInvalid = false;
		}
	}

	/**
	 * 处理登录失效
	 */
	public void dealLoginInvalid(){
		if (loginInvalid){

		}
	}

}