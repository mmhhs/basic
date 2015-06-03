package com.base.feima.baseproject.task;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.model.ResultModel;
import com.base.feima.baseproject.net.HttpUtil;
import com.base.feima.baseproject.net.Httpclient;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool;
import com.base.feima.baseproject.util.BaseConstant.TaskResult;
import com.base.feima.baseproject.util.JacksonUtil;

import java.io.File;
import java.util.List;
import java.util.Map;


public class ShowDialogTask extends BaseTask<Void, String, TaskResult>{
    public final static int POST = 1;
    public final static int GET = 2;
    public final static int PUT = 3;
    public final static int UPLOAD = 4;
    public final static int UPLOADS = 5;
    public final static int NET_ERROR = 6;
    public int accessType;
	public boolean showDialog = false;
	public boolean showNetToast = false;
	public PopupWindow loadPopupWindow;
	public Context context;
	public View parentView;
	public String loadsString = "";
	public String httpUrl = "";
	public Map<String, Object> argMap;
	public String errorMsg;
	public String resultsString = null;

	public List<File> fileList;
	public String keyString = "Filedata";
	public String keyString2 = "Filedata[]";
	public int taskFlag=0;
	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="ShowDialogTask";
	
	/**
	 * ���ش����ʱ�߳�
	 * @param context ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾ����parentView����Ϊ��
	 */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.tagString = tagString;
	}
	
	/**
	 * ��������߳�
	 * @param context ������
	 * @param parentView ������ͼ
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param accessType ���ʷ�ʽ
	 */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,int accessType){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		this.tagString = tagString;
	}
    /**
     * ��������߳� ���һ��������Ϊtrue����û������ʱ��������ʾ
     * @param context ������
     * @param parentView ������ͼ
     * @param loadsString ��ʾ����
     * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
     * @param httpUrl ����·��
     * @param argMap ��������
     * @param accessType ���ʷ�ʽ
     */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,int accessType,boolean showNet){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
		this.tagString = tagString;
		this.showNetToast = showNet;
	}
	/**
	 * ��������߳�-�ϴ��ļ�
	 * @param context ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param fileList �ļ�����
	 * @param accessType ���ʷ�ʽ
	 */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,int accessType){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.accessType = accessType;
		this.tagString = tagString;
	}
	/**
	 * ��������߳�-�ϴ��ļ�-�ļ���ʶ
	 * @param context ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param fileList �ļ�����
	 * @param key �������ж��ļ���ʶ
	 * @param accessType ���ʷ�ʽ
	 */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,String key,int accessType){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.keyString = key;
		this.keyString2 = key;
		this.accessType = accessType;
		this.tagString = tagString;
	}
    /**
     * ��������߳�-�ϴ��ļ�-�ļ���ʶ ���һ��������Ϊtrue����û������ʱ��������ʾ
     * @param context ������
     * @param parentView ������ͼ
     * @param loadsString ��ʾ����
     * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
     * @param httpUrl ����·��
     * @param argMap ��������
     * @param fileList �ļ�����
     * @param key �������ж��ļ���ʶ
     * @param accessType ���ʷ�ʽ
     */
	public ShowDialogTask(Context context,String tagString,View parentView,String loadsString,boolean showDialog,String httpUrl, Map<String, Object> argMap,List<File> fileList,String key,int accessType,boolean showNet){
		this.context = context;
		this.parentView = parentView;
		this.loadsString = loadsString;
		this.showDialog = showDialog;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.fileList = fileList;
		this.keyString = key;
		this.keyString2 = key;
		this.accessType = accessType;
		this.showNetToast = showNet;
		this.tagString = tagString;
	}
	
	@Override
	public void onPreExecute() 
	{	
		try {
			taskManager.addTask(tagString, this);
			if(loadsString.isEmpty()){
				loadsString = context.getResources().getString(R.string.task_item3);
			}
			if(!HttpUtil.isnet(context)){				
				taskFlag = NET_ERROR;
				if (showNetToast) {
					PublicTools.addToast(context, context.getResources().getString(R.string.net_tip));
				}
			}else {
				if (showDialog) {
					if(parentView==null){
						return;
					}
					loadPopupWindow = PopupwindowTool.showLoadWindow(context, parentView, loadsString, 0, 0, 0, 0);
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
			if (loadPopupWindow!=null) {
				loadPopupWindow.dismiss();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		switch(result){			
		case OK:					
			doOnOKListener(this);
			break;
		case ERROR:
			doOnERRORListener(this);				
			break;
		case CANCELLED:			
			PublicTools.addToast(context, context.getResources().getString(R.string.task_item1));
			break;
		default:
			break;
		}
		doOnDoneListener(this);
		taskManager.cancelOneTasks(this);
	}
	
	private OnBackgroundListener onBackgroundListener;
	
	public interface OnBackgroundListener{
		public TaskResult onBackground(ShowDialogTask showDialogTask);
	}	

	/**
	 * ��ִ̨�д���-�����ӿڻ����ʱ�¼�
	 * @param onBackgroundClickListener
	 */
	public void setOnBackgroundListener(
			OnBackgroundListener onBackgroundClickListener) {
		this.onBackgroundListener = onBackgroundClickListener;
	}

	private TaskResult doOnBackgroundListener(ShowDialogTask showDialogTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(onBackgroundListener!=null){
			taskResult = this.onBackgroundListener.onBackground(showDialogTask);
		}		
		return taskResult;
	}
	
	private OnOKListener onOKListener;
	
	public interface OnOKListener{
		public void onOK(ShowDialogTask showDialogTask);
	}	
	/**
	 * �ɹ�����
	 * @param onOKListener
	 */
	public void setOnOKListener(
			OnOKListener onOKListener) {
		this.onOKListener = onOKListener;
	}

	private void doOnOKListener(ShowDialogTask showDialogTask){
		if(onOKListener!=null){
			this.onOKListener.onOK(showDialogTask);
		}		
	}
	
	private OnErrorListener onErrorListener;
	
	public interface OnErrorListener{
		public void onError(ShowDialogTask showDialogTask);
	}	
	/**
	 * ������
	 * @param onErrorListener
	 */
	public void setOnERRORListener(
			OnErrorListener onErrorListener) {
		this.onErrorListener = onErrorListener;
	}

	private void doOnERRORListener(ShowDialogTask showDialogTask){
		if(onErrorListener!=null){
			this.onErrorListener.onError(showDialogTask);
		}		
	}
	
	private OnDoneListener onDoneListener;
	
	public interface OnDoneListener{
		public void onDone(ShowDialogTask showDialogTask);
	}	
	/**
	 * ��������
	 * @param onErrorListener
	 */
	public void setOnDoneListener(
			OnDoneListener onDoneListener) {
		this.onDoneListener = onDoneListener;
	}

	private void doOnDoneListener(ShowDialogTask showDialogTask){
		if(onDoneListener!=null){
			this.onDoneListener.onDone(showDialogTask);
		}		
	}
	
	/**
	 * Ĭ�Ϻ�̨�������ؽ��
	 */
	private OnBackgroundListener defaultBackgroundListener = new OnBackgroundListener(){

		@Override
		public TaskResult onBackground(ShowDialogTask showDialogTask) {
			// TODO Auto-generated method stub
			TaskResult taskResult = TaskResult.NOTHING;
			JacksonUtil json = JacksonUtil.getInstance();
			ResultModel res = json.readValue(resultsString, ResultModel.class);
			if(res!=null){
				if(PublicTools.judgeResult(context,""+res.getResult())){
					taskResult = TaskResult.OK;							
				}else{					
					taskResult = TaskResult.ERROR;
					errorMsg = PublicTools.judgeResult2(context,""+res.getResult());					
				}
			}else{
				taskResult = TaskResult.CANCELLED;
			}	
			return taskResult;
		}
		
	};

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public View getParentView() {
		return parentView;
	}

	public void setParentView(View parentView) {
		this.parentView = parentView;
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