package com.base.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnLoadBackgroundListener;
import com.base.feima.baseproject.listener.IOnLoadResultListener;
import com.base.feima.baseproject.listener.IOnTryClickListener;
import com.base.feima.baseproject.manager.TaskManager;
import com.base.feima.baseproject.model.ResultModel;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.ResultTools;
import com.base.feima.baseproject.tool.popupwindow.ViewTool;
import com.base.feima.baseproject.util.BaseConstant.TaskResult;
import com.base.feima.baseproject.util.JacksonUtil;
import com.base.feima.baseproject.util.StringUtils;
import com.base.feima.baseproject.util.net.HttpUtil;
import com.base.feima.baseproject.util.net.Httpclient;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ShowLoadTask extends BaseTask<Void, String, TaskResult> {
    //��ʾ���
    public final static int NET_ERROR = 6;//û������
    public int netFlag =0;//�����ʶ
	public boolean showLoad = false;
	public ViewTool viewTool;//��ͼ����
	public Activity activity;//������
	public View contentView;//���ݽ���
	public LinearLayout loadView;//���ؽ���
	public String loadString = "";//��������
    //�������
    public int accessType;//���ʷ�ʽ
	public String httpUrl = "";//����·��
	public Map<String, Object> argMap;//����
    public List<File> fileList;//�ϴ��ļ��б�
    public String keyString = "Filedata";//�ϴ��ļ���ֵ
    //����ֵ���
	public String errorMsg;//������Ϣ
	public String resultsString = null;	//����ֵ
    public boolean loginInvalid = false;//��¼ʧЧ

	public TaskManager taskManager = TaskManager.getTaskManagerInstance();
	public String tagString="ShowLoadTask";
    public IOnTryClickListener iOnTryClickListener;//���Լ���
    private IOnLoadBackgroundListener iOnLoadBackgroundListener;
    private IOnLoadResultListener iOnLoadResultListener;
	
	/**
	 * ���ش����ʱ�߳�
	 * @param activity           ������
	 * @param contentView       ������ͼ
	 * @param loadView          ������ͼ
	 * @param loadString        ��ʾ����
	 * @param showLoad          �Ƿ���ʾloadview ����ʾ����contentView��loadView����Ϊ��
	 */
	public ShowLoadTask(Activity activity,String tagString,View contentView,LinearLayout loadView,String loadString,boolean showLoad,IOnTryClickListener iOnTryClickListener){
		this.activity = activity;
        this.tagString = tagString;
		this.contentView = contentView;
        this.loadView = loadView;
		this.loadString = loadString;
		this.showLoad = showLoad;
		this.iOnTryClickListener = iOnTryClickListener;
        viewTool = new ViewTool();
	}
	
	/**
	 * ��������߳�
	 * @param activity           ������
	 * @param contentView       ������ͼ
	 * @param loadView          ������ͼ
	 * @param loadString        ��ʾ����
	 * @param showLoad          �Ƿ���ʾloadview ����ʾ����contentView��loadView����Ϊ��
	 * @param httpUrl           ����·��
	 * @param argMap            ��������
	 * @param accessType        ���ʷ�ʽ
	 */
	public ShowLoadTask(Activity activity,String tagString,View contentView,LinearLayout loadView,String loadString,boolean showLoad,IOnTryClickListener iOnTryClickListener,String httpUrl, Map<String, Object> argMap,int accessType){
        this.activity = activity;
        this.tagString = tagString;
        this.contentView = contentView;
        this.loadView = loadView;
        this.loadString = loadString;
        this.showLoad = showLoad;
        this.iOnTryClickListener = iOnTryClickListener;
		this.httpUrl = httpUrl;
		this.argMap = argMap;
		this.accessType = accessType;
        viewTool = new ViewTool();
	}
	
	@Override
	public void onPreExecute() 
	{	
		try {
			addTask();
			if(StringUtils.isEmpty(loadString)){
				loadString = activity.getResources().getString(R.string.pop_item1);
			}
			if(!HttpUtil.isnet(activity)){
				netFlag = NET_ERROR;
				if (showLoad) {
					if(contentView==null||loadView==null){
						return;
					}
					viewTool.addErrorView(activity, activity.getString(R.string.pop_item2),
							contentView, loadView, iOnTryClickListener);
				}else {
					PublicTools.addToast(activity, activity.getString(R.string.net_tip));
				}
			}else {
				if (showLoad) {
					if(contentView==null||loadView==null){
						return;
					}
					viewTool.addLoadView(activity, loadString, contentView, loadView);
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
        //���������
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
			if (iOnLoadBackgroundListener==null) {
                iOnLoadBackgroundListener = defaultBackgroundListener;
			}
			taskResult = doOnBackgroundListener(this);
		}
		return taskResult;
	}
	
	@Override
	public void onPostExecute(TaskResult result)
	{
		try {
			if (netFlag == NET_ERROR) {
				return;
			}
			if(showLoad){
				viewTool.removeLoadView(contentView, loadView);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
        if (iOnLoadResultListener!=null){
            iOnLoadResultListener.onDone(this);
        }
		switch(result){			
		case OK:					
			if (iOnLoadResultListener!=null){
                iOnLoadResultListener.onOK(this);
            }
			break;
		case ERROR:
			if (showLoad) {
				viewTool.addErrorView(activity, activity.getString(R.string.pop_item3),
						contentView, loadView, iOnTryClickListener);
			}
			if (iOnLoadResultListener!=null){
                iOnLoadResultListener.onError(this);
            }
            dealLoginInvalid();
			break;
		case CANCELLED:
			if (showLoad) {
				viewTool.addErrorView(activity, activity.getString(R.string.task_item1),
						contentView, loadView, iOnTryClickListener);
			}			
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


	private TaskResult doOnBackgroundListener(ShowLoadTask showLoadTask){
		TaskResult taskResult = TaskResult.NOTHING;
		if(iOnLoadBackgroundListener!=null){
			taskResult = this.iOnLoadBackgroundListener.onBackground(showLoadTask);
		}		
		return taskResult;
	}
	
	/**
	 * Ĭ�Ϻ�̨�������ؽ��
	 */
	private IOnLoadBackgroundListener defaultBackgroundListener = new IOnLoadBackgroundListener(){

		@Override
		public TaskResult onBackground(ShowLoadTask showLoadTask) {
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
                    judgeLoginInvalid(""+res.getCode());
				}
			}else{
				taskResult = TaskResult.CANCELLED;
			}	
			return taskResult;
		}
	};

    public void setiOnTryClickListener(IOnTryClickListener iOnTryClickListener) {
        this.iOnTryClickListener = iOnTryClickListener;
    }

    public void setiOnLoadBackgroundListener(IOnLoadBackgroundListener iOnLoadBackgroundListener) {
        this.iOnLoadBackgroundListener = iOnLoadBackgroundListener;
    }

    public void setiOnLoadResultListener(IOnLoadResultListener iOnLoadResultListener) {
        this.iOnLoadResultListener = iOnLoadResultListener;
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