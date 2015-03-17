package com.example.zf_android.trade.entity;

import java.io.Serializable;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyBank implements Serializable {

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
