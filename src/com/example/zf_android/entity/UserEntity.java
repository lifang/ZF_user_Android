package com.example.zf_android.entity;

public class UserEntity {
//    "updatedAt": 1427099891000,
//    "id": 188,
	private int id;
//    "phone": "15050411623",
	private String phone;
//    "lastLoginedAt": 1427161088000,
//    "username": "15050411623",
	private String username;
//    "status": 2,
	private int status;
//    "cityId": 90,
	private int cityId;
	private int accountType;
//    "createdAt": 1427086468000,
//    "accountType": 0,
//    "types": 1
	private int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
