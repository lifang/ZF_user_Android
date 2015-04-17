package com.example.zf_android.entity;

public class MyinfoEntity {
	//city_id":344,"phone":"15638981775","email":"645657261@qq.com",
	//"name":"马克思","password":"e10adc3949ba59abbe56e057f20f883e","integral":100
	private String city_id;
	private String phone;
	private String email;
	private String name;
	private String password;
	
	private String integral;
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
