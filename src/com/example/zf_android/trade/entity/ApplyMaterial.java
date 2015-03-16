package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/6.
 */
public class ApplyMaterial {

	private int id;

	@SerializedName("info_type")
	private int types;

	private String name;

	private int openingRequirementId;

	private String value;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpeningRequirementId() {
		return openingRequirementId;
	}

	public void setOpeningRequirementId(int openingRequirementId) {
		this.openingRequirementId = openingRequirementId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
