package com.base.feima.baseproject.model.user;


import com.base.feima.baseproject.model.ResultModel;

public class UserResult extends ResultModel{
	public UserModel data;
	public UserModel getData() {
		return data;
	}
	public void setData(UserModel data) {
		this.data = data;
	}
	
}