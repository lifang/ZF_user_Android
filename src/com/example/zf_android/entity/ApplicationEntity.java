package com.example.zf_android.entity;

import java.util.List;

public class ApplicationEntity {
//	{"id":4,
//		"serial_num":"t20150004",
//		"openStatus":[{"trade_value":"消费","status":1},{"trade_value":"转账","status":2}]}
	private int id;
	private String serial_num;
	private List<OpenStateEntity> openStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public List<OpenStateEntity> getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(List<OpenStateEntity> openStatus) {
		this.openStatus = openStatus;
	}
	
} 
