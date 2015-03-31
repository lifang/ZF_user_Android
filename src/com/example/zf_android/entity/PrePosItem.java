package com.example.zf_android.entity;

import java.util.List;

public class PrePosItem {
//	"id": 1,
//    "value": "虚拟",
//    "son": [
	public Boolean isCheck;
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	private int id;
	private String value;
	private List<PosItem> son;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<PosItem> getSon() {
		return son;
	}
	public void setSon(List<PosItem> son) {
		this.son = son;
	}
}
