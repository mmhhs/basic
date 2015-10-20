package com.base.feima.baseproject.model.user;


import com.base.feima.baseproject.model.ResultEntity;

public class UserResultEntity extends ResultEntity {
	public UserModelEntity data;
	public UserModelEntity getData() {
		return data;
	}
	public void setData(UserModelEntity data) {
		this.data = data;
	}
	
}