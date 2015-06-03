package com.base.feima.baseproject.model.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName="ZADUSERINFO")
public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -195621665514151273L;
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
	@DatabaseField
	public String album_count="";
	@DatabaseField
	public String order_count="";//用户完成订单数
	@DatabaseField
	public String order_count2="";//美甲师完成订单数
	
	@DatabaseField
	public String password="";
	@DatabaseField
	public String user_name="";
	@DatabaseField
	public String age="";
	@DatabaseField
	public String province="";
	@DatabaseField
	public String city="";	
	@DatabaseField
	public String district="";
	@DatabaseField
	public String street="";
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getHead_image() {
		return head_image;
	}
	public void setHead_image(String head_image) {
		this.head_image = head_image;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getBy_attention() {
		return by_attention;
	}
	public void setBy_attention(String by_attention) {
		this.by_attention = by_attention;
	}
	public String getIs_attention() {
		return is_attention;
	}
	public void setIs_attention(String is_attention) {
		this.is_attention = is_attention;
	}
	public String getAlbum_count() {
		return album_count;
	}
	public void setAlbum_count(String album_count) {
		this.album_count = album_count;
	}
	public String getOrder_count() {
		return order_count;
	}
	public void setOrder_count(String order_count) {
		this.order_count = order_count;
	}
	public String getOrder_count2() {
		return order_count2;
	}
	public void setOrder_count2(String order_count2) {
		this.order_count2 = order_count2;
	}
	
	
}