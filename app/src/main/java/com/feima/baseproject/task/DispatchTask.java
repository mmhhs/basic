package com.feima.baseproject.task;

import android.app.Activity;
import android.view.View;

import com.feima.baseproject.listener.IOnBackgroundListener;
import com.feima.baseproject.listener.IOnEncryptListener;
import com.feima.baseproject.listener.IOnResultListener;
import com.feima.baseproject.manager.TaskManager;
import com.feima.baseproject.model.ResultEntity;
import com.feima.baseproject.task.TaskConstant.TaskResult;
import com.feima.baseproject.util.ResultUtil;
import com.feima.baseproject.util.net.Httpclient;
import com.feima.baseproject.util.tool.JacksonUtil;
import com.feima.baseproject.util.tool.StringUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DispatchTask extends BaseTask<Void, String, TaskResult> {
	//显示相关
	public final static int NET_ERROR = 6;//没有网络
	public int netFlag =0;//网络标识
	public boolean showLoad = false;//显示加载框
	public Activity activity;//上下文
	public String loadString = "";//加载文字
	public boolean showTipSuccess = false;//成功时显示提示信息
	public boolean showTipError = true;//错误时显示提示信息
	//访问相关
	public int accessType;//访问方式
	public String httpUrl = "";//网络路径
	public Map<String, Object> argMap;//参数
	public List<File> fileList;//上传文件列表
	public String keyString = "Filedata";//上传文件键值
	//返回值相关
	public String resultMsg;//返回提示信息
	public String resultsString = null;	//返回值
	public boolean loginInvalid = false;//登录失效

	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="DispatchTask";
	public IOnBackgroundListener iOnBackgroundListener;
	public IOnResultListener iOnResultListener;
	public IOnEncryptListener iOnEncryptListener;
	public ResultEntity resultEntity;//返回值解析结果父类 向上转型
	public Class parseClass;//用于解析的实体类
	public View view;//子类继承需设置



	public DispatchTask() {
		init();
	}

	public void init(){
		parseClass = ResultEntity.class;
		addTask();

	}

	@Override
	public void onPreExecute()
	{

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
		//无网络情况
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
		if (!isCancelled()){
			if(StringUtil.isEmpty(resultsString)){
				taskResult = TaskResult.CANCELLED;
			}else{
				if (iOnBackgroundListener==null) {
					iOnBackgroundListener = defaultBackgroundListener;
				}
				taskResult = doOnBackgroundListener(this);
			}
		}
		return taskResult;
	}

	@Override
	public void onPostExecute(TaskResult result)
	{
		removeTask();
	}

	@Override
	public void onCancelled() {
		removeTask();
		super.onCancelled();
	}


	public void addTask(){
		taskManager.addTask(tagString, this);
	}

	public void removeTask(){
		taskManager.removeTask(this);
	}


	public TaskResult doOnBackgroundListener(DispatchTask showLoadTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(iOnBackgroundListener!=null){
			taskResult = this.iOnBackgroundListener.onBackground(showLoadTask);
		}
		return taskResult;
	}

	/**
	 * 默认后台解析返回结果
	 */
	public IOnBackgroundListener defaultBackgroundListener = new IOnBackgroundListener(){

		@Override
		public TaskResult onBackground(DispatchTask task) {
			// TODO Auto-generated method stub
			TaskResult taskResult = TaskResult.NOTHING;
			JacksonUtil json = JacksonUtil.getInstance();
			resultEntity = (ResultEntity)json.readValue(resultsString, parseClass);
			if(resultEntity !=null){
				resultMsg = resultEntity.getMsg();
				if(ResultUtil.judgeResult(activity, "" + resultEntity.getCode())){
					taskResult = TaskResult.OK;
				}else{
					taskResult = TaskResult.ERROR;
					judgeLoginInvalid(""+ resultEntity.getCode());
				}
			}else{
				taskResult = TaskResult.CANCELLED;
			}
			return taskResult;
		}
	};


	public void setiOnBackgroundListener(IOnBackgroundListener iOnBackgroundListener) {
		this.iOnBackgroundListener = iOnBackgroundListener;
	}

	public void setiOnResultListener(IOnResultListener iOnResultListener) {
		this.iOnResultListener = iOnResultListener;
	}

	public void setiOnEncryptListener(IOnEncryptListener iOnEncryptListener) {
		this.iOnEncryptListener = iOnEncryptListener;
	}

	public String getResultsString() {
		return resultsString;
	}

	public void setResultsString(String resultsString) {
		this.resultsString = resultsString;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Class getParseClass() {
		return parseClass;
	}

	/**
	 * 设置解析实体类
	 * @param parseClass
     */
	public void setParseClass(Class parseClass) {
		this.parseClass = parseClass;
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