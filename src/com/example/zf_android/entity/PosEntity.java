package com.example.zf_android.entity;

public class PosEntity {
	//volume_number":123,"id":2,"good_brand":"Ʒ��1","total_score":1,
	//"retail_price":72464,"pay_channe":"ͨ��2",
	//"Title":"̩ɽPos�콢��2","Model_number":"�ͺ�10"}
	
//    "volume_number": 413,
//    "id": 1,
//    "url_path": "http://picture.youth.cn/qtdb/201503/W020150305443475703964.jpg",
//    "retail_price": 12415,
//    "Title": "泰山Pos旗舰版1"
	
	private String url_path;
	public String getUrl_path() {
		return url_path;
	}
	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}
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
