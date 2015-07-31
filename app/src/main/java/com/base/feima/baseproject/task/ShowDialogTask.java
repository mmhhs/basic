package com.base.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.listener.IOnDialogResultListener;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.model.ResultModel;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.ResultTools;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool;
import com.base.feima.baseproject.util.BaseConstant.TaskResult;
import com.base.feima.baseproject.util.JacksonUtil;
import com.base.feima.baseproject.util.StringUtils;
import com.base.feima.baseproject.util.net.HttpUtil;
import com.base.feima.baseproject.util.net.Httpclient;

import java.io.File;
import java.util.List;
import java.util.Map;


public class ShowDialogTask extends BaseTask<Void, String, TaskResult>{
    //��ʾ���
    public final static int NET_ERROR = 6;//û������
    public int netFlag =0;//�����ʶ
    public Activity activity;//������
    public View parentView;//������ͼ
	public boolean showDialog = false;//��ʾ���ؿ�
	public boolean showNetToast = false;//��ʾ��������toast
	public PopupWindow loadPopupWindow;//���ؿ�
	public String loadString = "";//��������
    //�������
    public int accessType;//���ʷ�ʽ
	public String httpUrl = "";//����·��
	public Map<String, Object> argMap;//����
	public List<File> fileList;//�ϴ��ļ��б�
	public String keyString = "Filedata";//�ϴ��ļ���ֵ
    //����ֵ���
    public String errorMsg;//������Ϣ
    public String resultsString = null;//����ֵ
    public boolean loginInvalid = false;//��¼ʧЧ

	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="ShowDialogTask";
    private IOnDialogResultListener iOnDialogResultListener;
    private IOnDialogBackgroundListener iOnDialogBackgroundListener;
	private PopupwindowTool popupwindowTool;
	
	/**
	 * ���ش����ʱ�߳�
	 * @param activity ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾ����parentView����Ϊ��
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
	 * ��������߳�
	 * @param activity ������
	 * @param parentView ������ͼ
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param accessType ���ʷ�ʽ
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
     * ��������߳� ���һ��������Ϊtrue����û������ʱ��������ʾ
     * @param activity ������
     * @param parentView ������ͼ
     * @param loadsString ��ʾ����
     * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
     * @param httpUrl ����·��
     * @param argMap ��������
     * @param accessType ���ʷ�ʽ
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
	 * ��������߳�-�ϴ��ļ�
	 * @param activity ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param fileList �ļ�����
	 * @param accessType ���ʷ�ʽ
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
	 * ��������߳�-�ϴ��ļ�-�ļ���ʶ
	 * @param activity ������
	 * @param parentView ������ͼ 
	 * @param loadsString ��ʾ����
	 * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
	 * @param httpUrl ����·��
	 * @param argMap ��������
	 * @param fileList �ļ�����
	 * @param key �������ж��ļ���ʶ
	 * @param accessType ���ʷ�ʽ
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
     * ��������߳�-�ϴ��ļ�-�ļ���ʶ ���һ��������Ϊtrue����û������ʱ��������ʾ
     * @param activity ������
     * @param parentView ������ͼ
     * @param loadsString ��ʾ����
     * @param showDialog �Ƿ���ʾdialog ����ʾparentView����Ϊ��
     * @param httpUrl ����·��
     * @param argMap ��������
     * @param fileList �ļ�����
     * @param key �������ж��ļ���ʶ
     * @param accessType ���ʷ�ʽ
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
		popupwindowTool = new PopupwindowTool(activity);
	}
	
	@Override
	public void onPreExecute() 
	{	
		try {
            addTask();
			if(StringUtils.isEmpty(loadString)){
				loadString = activity.getResources().getString(R.string.task_item3);
			}
			if(!HttpUtil.isnet(activity)){
				netFlag = NET_ERROR;
				if (showNetToast) {
					PublicTools.addToast(activity, activity.getResources().getString(R.string.net_tip));
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
		//��������������
		if(StringUtils.isEmpty(httpUrl)){
			taskResult = doOnBackgroundListener(this);
			return taskResult;
		}
		if (netFlag == NET_ERROR) {
			return taskResult;
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
		if(StringUtils.isEmpty(resultsString)){
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
                if (!StringUtils.isEmpty(httpUrl)){
                    PublicTools.addToast(activity, ""+errorMsg);
                }
                dealLoginInvalid();
                break;
            case CANCELLED:
                PublicTools.addToast(activity, activity.getString(R.string.task_item1));
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

    private TaskResult doOnBackgroundListener(ShowDialogTask showDialogTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(iOnDialogBackgroundListener!=null){
			taskResult = this.iOnDialogBackgroundListener.onBackground(showDialogTask);
		}		
		return taskResult;
	}

	
	/**
	 * Ĭ�Ϻ�̨�������ؽ��
	 */
	private IOnDialogBackgroundListener defaultBackgroundListener = new IOnDialogBackgroundListener(){

		@Override
		public TaskResult onBackground(ShowDialogTask showDialogTask) {
			// TODO Auto-generated method stub
			TaskResult taskResult = TaskResult.NOTHING;
			JacksonUtil json = JacksonUtil.getInstance();
			ResultModel res = json.readValue(resultsString, ResultModel.class);
			if(res!=null){
				if(ResultTools.judgeResult(activity, "" + res.getCode())){
					taskResult = TaskResult.OK;							
				}else{					
					taskResult = TaskResult.ERROR;
					errorMsg = res.getMsg();
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
     * �жϵ�¼ʧЧ
     */
	public void judgeLoginInvalid(String code){
        if (ResultTools.judgeLoginInvalid(activity, "" + code)){
            loginInvalid = true;
        }else {
            loginInvalid = false;
        }
    }

    /**
     * �����¼ʧЧ
     */
    public void dealLoginInvalid(){
        if (loginInvalid){

        }
    }
	
}