package com.example.zf_android.entity;
/**
 * 
*    
* 类名称：User   
* 类描述：   用户信息实体类
* 创建人： ljp  
* 创建时间：2014-12-4 下午2:19:47   
* @version    
*
 */
public class User {
	//"result":{"studentEmail":"475813996@qq.com","studentId":6,"studentStatus":2,"studentMobilePhone":"18862243513"}
	private String studentId;
	private String studentMobilePhone;
	private String studentEmail;
 
	private String studentStatus;
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentMobilePhone() {
		return studentMobilePhone;
	}
	public void setStudentMobilePhone(String studentMobilePhone) {
		this.studentMobilePhone = studentMobilePhone;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentStatus() {
		return studentStatus;
	}
	public void setStudentStatus(String authority) {
		this.studentStatus = authority;
	}
	
}
