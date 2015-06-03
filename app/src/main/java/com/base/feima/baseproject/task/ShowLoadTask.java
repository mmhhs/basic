package com.base.feima.baseproject.task;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.model.ResultModel;
import com.base.feima.baseproject.net.HttpUtil;
import com.base.feima.baseproject.net.Httpclient;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.popupwindow.ViewTool;
import com.base.feima.baseproject.tool.popupwindow.ViewTool.OnTryClickListener;
import com.base.feima.baseproject.util.BaseConstant.TaskResult;
import com.base.feima.baseproject.util.JacksonUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ShowLoadTask extends BaseTask<Void, String, TaskResult> {
	public boolean showLoad = false;
	public ViewTool viewTool;
	public Context context;
	public View contentView;
	public LinearLayout loadView;
	public OnTryClickListener onTryClickListener;
	public String loadsString = "";
	public String httpUrl = "";
	public Map<String, Object> argMap;
	public final static int POST = 1;
	public final static int GET = 2;
	public final static int PUT = 3;
	public final static int UPLOAD = 4;
	public final static int UPLOADS = 5;
	public final static int NET_ERROR = 6;
	public int accessType;
	public String errorMsg;
	public String resultsString = null;	
	public List<File> fileList;
	public String keyString = "Filedata";
	public String keyString2 = "Filedata[]";
	public int taskFlag=0;
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="ShowLoadTask";
	
	/**
	 * ���ش����ʱ�߳�
	 * @param context ������
	 * @param contentView ������ͼ 
	 * @param loadView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showLoad �Ƿ���ʾloadview ����ʾ����contentView��loadView����Ϊ��
	 */
	public ShowLoadTask(Context context,String tagString,View contentView,LinearLayout loadView,String loadsString,boolean showLoad,OnTryClickListener onTryClickListener){
		this.context = context;
		this.contentView = contentView;
		this.loadsString = loadsString;
		this.showLoad = showLoad;
		this.loadView = loadView;
		this.onTryClickListener = onTryClickListener;
		viewTool = new ViewTool();
		this.tagString = tagString;
	}
	
	/**
	 * ��������߳�
	 * @param context ������
	 * @param contentView ������ͼ 
	 * @param loadView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showLoad �Ƿ���ʾloadview ����ʾ����contentView��loadView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param accessType ���ʷ�ʽ
	 */
	public ShowLoadTask(Context context,String tagString,View contentView,LinearLayout loadView,String loadsString,boolean showLoad,OnTryClickListener onTryClickListener,String httpUrl, Map<String, Object> argMap,int accessType){
		this.context = context;
		this.contentView = contentView;
		this.loadsString = loadsString;
		this.showLoad = showLoad;
		this.onTryClickListener = onTryClickListener;
		this.loadView = loadView;		
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		viewTool = new ViewTool();
		this.tagString = tagString;
	}
	
	@Override
	public void onPreExecute() 
	{	
		try {
			addTask();
			if(loadsString.isEmpty()){
				loadsString = context.getResources().getString(R.string.pop_item1);
			}
			if(!HttpUtil.isnet(context)){
				taskFlag = NET_ERROR;
				if (showLoad) {
					if(contentView==null||loadView==null){
						return;
					}
					viewTool.addErrorView(context, context.getResources().getString(R.string.pop_item2),
							contentView, loadView, onTryClickListener);
				}else {
					PublicTools.addToast(context, context.getResources().getString(R.string.net_tip));
				}
			}else {
				if (showLoad) {
					if(contentView==null||loadView==null){
						return;
					}
					viewTool.addLoadView(context, loadsString, contentView, loadView);
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
		//��������������
		if(httpUrl.isEmpty()){
			taskResult = doOnBackgroundListener(this);
			return taskResult;
		}
		if (taskFlag==NET_ERROR) {
			return taskResult;
		}
		switch (accessType) {
		case POST:
			try {
				resultsString = Httpclient.POSTMethod(httpUrl, argMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case PUT:
			try {
				resultsString = Httpclient.PUTMethod(httpUrl, argMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			break;
		case GET:
			try {
				resultsString = Httpclient.GETMethod(httpUrl, argMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case UPLOAD:
			try {
				resultsString = Httpclient.uploadSubmitFile2(httpUrl, argMap, fileList.get(0), keyString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case UPLOADS:
			try {
				resultsString = Httpclient.uploadSubmitFiles2(httpUrl, argMap, fileList, keyString2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		if(resultsString==null){				
			taskResult = TaskResult.CANCELLED;
		}else{
			if (onBackgroundListener==null) {
				onBackgroundListener = defaultBackgroundListener;				
			}
			taskResult = doOnBackgroundListener(this);
		}
				
		return taskResult;
	}
	
	@Override
	public void onPostExecute(TaskResult result)
	{
		try {
			if (taskFlag==NET_ERROR) {
				return;
			}
			if(showLoad){
				viewTool.removeLoadView(contentView, loadView);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		switch(result){			
		case OK:					
			doOnOKListener(this);
			break;
		case ERROR:
			if (showLoad) {
				viewTool.addErrorView(context, context.getResources().getString(R.string.pop_item3),
						contentView, loadView, onTryClickListener);
			}
			doOnERRORListener(this);				
			break;
		case CANCELLED:
			if (showLoad) {
				viewTool.addErrorView(context, context.getResources().getString(R.string.task_item1),
						contentView, loadView, onTryClickListener);
			}			
			break;
		default:
			break;
		}
		doOnDoneListener(this);
		cancelTask();
	}
	
	public void addTask(){
		taskManager.addTask(tagString, this);
	}
	
	public void cancelTask(){
		taskManager.cancelOneTasks(this);
	}
	
	private OnBackgroundListener onBackgroundListener;
	
	public interface OnBackgroundListener{
		public TaskResult onBackground(ShowLoadTask showDialogTask);
	}	

	/**
	 * ��ִ̨�д���-�����ӿڻ����ʱ�¼�
	 * @param onBackgroundClickListener
	 */
	public void setOnBackgroundListener(
			OnBackgroundListener onBackgroundClickListener) {
		this.onBackgroundListener = onBackgroundClickListener;
	}

	private TaskResult doOnBackgroundListener(ShowLoadTask showDialogTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(onBackgroundListener!=null){
			taskResult = this.onBackgroundListener.onBackground(showDialogTask);
		}		
		return taskResult;
	}
	
	private OnLoadOKListener onOKListener;
	
	public interface OnLoadOKListener{
		public void onOK(ShowLoadTask showLoadTask);
	}	
	/**
	 * �ɹ�����
	 * @param onOKListener
	 */
	public void setOnOKListener(
			OnLoadOKListener onOKListener) {
		this.onOKListener = onOKListener;
	}

	private void doOnOKListener(ShowLoadTask showLoadTask){
		if(onOKListener!=null){
			this.onOKListener.onOK(showLoadTask);
		}		
	}
	
	private OnERRORListener onERRORListener;
	
	public interface OnERRORListener{
		public void onERROR(ShowLoadTask showLoadTask);
	}	
	/**
	 * ������
	 * @param onERRORListener
	 */
	public void setOnERRORListener(
			OnERRORListener onERRORListener) {
		this.onERRORListener = onERRORListener;
	}

	private void doOnERRORListener(ShowLoadTask showLoadTask){
		if(onERRORListener!=null){
			this.onERRORListener.onERROR(showLoadTask);
		}		
	}
	
	private OnDoneListener onDoneListener;
	
	public interface OnDoneListener{
		public void onDone(ShowLoadTask showLoadTask);
	}	
	/**
	 * ��������
	 * @param onERRORListener
	 */
	public void setOnDoneListener(
			OnDoneListener onDoneListener) {
		this.onDoneListener = onDoneListener;
	}

	private void doOnDoneListener(ShowLoadTask showLoadTask){
		if(onDoneListener!=null){
			this.onDoneListener.onDone(showLoadTask);
		}		
	}
	
	/**
	 * Ĭ�Ϻ�̨�������ؽ��
	 */
	private OnBackgroundListener defaultBackgroundListener = new OnBackgroundListener(){

		@Override
		public TaskResult onBackground(ShowLoadTask showLoadTask) {
			// TODO Auto-generated method stub
			TaskResult taskResult = TaskResult.NOTHING;
			JacksonUtil json = JacksonUtil.getInstance();
			ResultModel res = json.readValue(resultsString, ResultModel.class);
			if(res!=null){
				if(PublicTools.judgeResult(context, "" + res.getResult())){
					taskResult = TaskResult.OK;
				}else{					
					taskResult = TaskResult.ERROR;
					errorMsg = PublicTools.judgeResult2(context, "" + res.getResult());
				}
			}else{
				taskResult = TaskResult.CANCELLED;
			}	
			return taskResult;
		}
		
	};

	
	public boolean isShowLoad() {
		return showLoad;
	}

	public void setShowLoad(boolean showLoad) {
		this.showLoad = showLoad;
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	public LinearLayout getLoadView() {
		return loadView;
	}

	public void setLoadView(LinearLayout loadView) {
		this.loadView = loadView;
	}

	public String getLoadsString() {
		return loadsString;
	}

	public void setLoadsString(String loadsString) {
		this.loadsString = loadsString;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getResultsString() {
		return resultsString;
	}

	public void setResultsString(String resultsString) {
		this.resultsString = resultsString;
	}
	
	
}