package com.example.zf_android.entity;

import org.litepal.crud.DataSupport;

public class PicEntity extends DataSupport{
	//id":5,
//	"picture_url":"http://file.youboy.com/a/142/67/57/6/660666.jpg",
	//"website_url":"http://baidu.com"
	private int id;
	private String picture_url;
	private String website_url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getWebsite_url() {
		return website_url;
	}
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}
	
	
}
