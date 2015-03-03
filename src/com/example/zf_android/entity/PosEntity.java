package com.example.zf_android.entity;

public class PosEntity {
	//volume_number":123,"id":2,"good_brand":"品牌1","total_score":1,
	//"retail_price":72464,"pay_channe":"通道2",
	//"Title":"泰山Pos旗舰版2","Model_number":"型号10"}
	private Boolean has_lease;
	
	public Boolean getHas_lease() {
		return has_lease;
	}
	public void setHas_lease(Boolean has_lease) {
		this.has_lease = has_lease;
	}
	private int volume_number;
	private int id;
	private String good_brand;
	private int total_score;
	private int retail_price;
	private String pay_channe;
	private String Title;
	private String Model_number;
	public int getVolume_number() {
		return volume_number;
	}
	public void setVolume_number(int volume_number) {
		this.volume_number = volume_number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGood_brand() {
		return good_brand;
	}
	public void setGood_brand(String good_brand) {
		this.good_brand = good_brand;
	}
	public int getTotal_score() {
		return total_score;
	}
	public void setTotal_score(int total_score) {
		this.total_score = total_score;
	}
	public int getRetail_price() {
		return retail_price;
	}
	public void setRetail_price(int retail_price) {
		this.retail_price = retail_price;
	}
	public String getPay_channe() {
		return pay_channe;
	}
	public void setPay_channe(String pay_channe) {
		this.pay_channe = pay_channe;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getModel_number() {
		return Model_number;
	}
	public void setModel_number(String model_number) {
		Model_number = model_number;
	}
	
	
	
	
	
}
