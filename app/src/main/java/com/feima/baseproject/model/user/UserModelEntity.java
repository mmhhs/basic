package com.feima.baseproject.model.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="ZADUSERINFO")
public class UserModelEntity {
	@DatabaseField(id=true)
	public String user_id;
	@DatabaseField
	public String role="";
	@DatabaseField
	public String head_image="";	
	@DatabaseField
	public String nick_name="";
	@DatabaseField
	public String real_name="";	
	@DatabaseField
	public String tel="";
	@DatabaseField
	public String sex="";	
	@DatabaseField
	public String attention="";
	@DatabaseField
	public String by_attention="";
	@DatabaseField
	public String is_attention="";
	@DatabaseField
	public String score="";	
	@DatabaseField
	public String longitude="";
	@DatabaseField
	public String latitude="";
	@DatabaseField
	public String address="";
	@DatabaseField
	public String add_time="";
	
}