package com.base.feima.baseproject.task;

import android.content.Context;

import com.base.feima.baseproject.model.user.UserModelEntity;
import com.base.feima.baseproject.model.user.UserResultEntity;
import com.base.feima.baseproject.util.net.Httpclient;
import com.base.feima.baseproject.util.ResultUtil;
import com.base.feima.baseproject.util.tool.JacksonUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Map;

public class DownloadTask implements Runnable{  
	private String fieldId;
	private String httpUrl;
	private Map<String, Object> argList;
	private Context context;
	private Dao<UserModelEntity, String> userInfoDao;
	
    public DownloadTask(Context context,String fieldId,String httpUrl,Map<String, Object> argList,Dao<UserModelEntity, String> userInfoDao){
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
			UserResultEntity res = json.readValue(result, UserResultEntity.class);
			if(res!=null){
				if(ResultUtil.judgeResult(context, "" + res.getCode())){
					UserModelEntity userModel = res.getData();

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