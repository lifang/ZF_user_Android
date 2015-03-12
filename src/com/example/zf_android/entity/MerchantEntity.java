package com.example.zf_android.entity;

public class MerchantEntity {
	private int id;
	private String title;
	private String legal_person_name;
	
	public Boolean getIscheck() {
		return Ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		Ischeck = ischeck;
	}
	private Boolean Ischeck;
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
	public String getLegal_person_name() {
		return legal_person_name;
	}
	public void setLegal_person_name(String legal_person_name) {
		this.legal_person_name = legal_person_name;
	}
	
}
