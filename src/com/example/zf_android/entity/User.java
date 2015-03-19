package com.example.zf_android.entity;
/**
 * 
*    
* ����ƣ�User   
* ��������   �û���Ϣʵ����
* �����ˣ� ljp  
* ����ʱ�䣺2014-12-4 ����2:19:47   
* @version    
*
 */
public class User {
	//"result":{"studentEmail":"475813996@qq.com","studentId":6,"studentStatus":2,"studentMobilePhone":"18862243513"}
//    "id": 17,
//    "username": "xianfeihu",
//    "password": "123123",
//    "accountType": null,
//    "name": null,
//    "cityId": null,
//    "types": 0,
//    "lastLoginedAt": null,
//    "integral": null,
//    "status": 1,
//    "deviceCode": null,
//    "createdAt": null,
//    "updatedAt": null,
//    "phone": null,
//    "email": null,
//    "code": null,
//    "dentcode": null
	private int id;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String username;
	private int types;
	private int status;
	private String phone;
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	
}
