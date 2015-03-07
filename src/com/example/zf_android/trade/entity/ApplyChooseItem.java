package com.example.zf_android.trade.entity;

import java.io.Serializable;

/**
 * Created by Leo on 2015/3/6.
 */
public class ApplyChooseItem implements Serializable {

	private int id;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
