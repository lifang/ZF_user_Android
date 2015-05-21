package com.example.zf_android.entity;

public class VersionEntity {
//	"id": 1,
//	"types": 1,
//	"versions": "1.0",
//	"down_url": "http://xxxxxx"
	private int id;
	private int types;
	private String versions;
	private String down_url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
	public String getDown_url() {
		return down_url;
	}
	public void setDown_url(String down_url) {
		this.down_url = down_url;
	}
	

	
}
