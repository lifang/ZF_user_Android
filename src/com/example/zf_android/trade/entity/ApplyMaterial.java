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

	private int opening_requirements_id;

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
		return opening_requirements_id;
	}

	public void setOpeningRequirementId(int openingRequirementId) {
		this.opening_requirements_id = openingRequirementId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
