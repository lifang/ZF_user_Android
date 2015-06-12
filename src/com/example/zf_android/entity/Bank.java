package com.example.zf_android.entity;

import java.io.Serializable;

public class Bank implements Serializable {

	private String no;

	private String name;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
