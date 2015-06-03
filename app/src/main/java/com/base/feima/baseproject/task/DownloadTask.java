package com.base.feima.baseproject.task;

import android.content.Context;

import com.base.feima.baseproject.model.user.UserModel;
import com.base.feima.baseproject.model.user.UserResult;
import com.base.feima.baseproject.net.Httpclient;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.util.JacksonUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Map;

public class DownloadTask implements Runnable{  
	private String fieldId;
	private String httpUrl;
	private Map<String, Object> argList;
	private Context context;
	private Dao<UserModel, String> userInfoDao;
	
    public DownloadTask(Context context,String fieldId,String httpUrl,Map<String, Object> argList,Dao<UserModel, String> userInfoDao){
        this.context = context;
    	this.httpUrl=httpUrl;  
    	this.argList = argList;
    	this.userInfoDao = userInfoDao;
    	this.fieldId = fieldId;
    }  
    
    @Override  
    public void run() {  
    	String result = null;
		try {
			result = Httpclient.POSTMethod(httpUrl, argList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
		if(result==null){				
			
		}else{
			JacksonUtil json = JacksonUtil.getInstance();
			UserResult res = json.readValue(result, UserResult.class);
			if(res!=null){
				if(PublicTools.judgeResult(context, "" + res.getResult())){
					UserModel userModel = res.getData();
					userModel.setUser_id(fieldId);
					try {
						userInfoDao.createOrUpdate(userModel);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{					
					
				}
			}else{
				
			}
			
		}
        
    }  
    public String getFileId(){  
        return fieldId;  
    }  
  
}  