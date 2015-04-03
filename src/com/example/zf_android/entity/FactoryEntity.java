package com.example.zf_android.entity;

import java.io.Serializable;

public class FactoryEntity implements Serializable {
	private int id;
	private int status;
	private String description;
	private String name;
	private String website_url;
	private int types;
	private String logo_file_path;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebsite_url() {
		return website_url;
	}
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public String getLogo_file_path() {
		return logo_file_path;
	}
	public void setLogo_file_path(String logo_file_path) {
		this.logo_file_path = logo_file_path;
	}
	
}
