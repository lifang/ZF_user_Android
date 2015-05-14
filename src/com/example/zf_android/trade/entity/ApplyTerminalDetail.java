package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/6.
 */
public class ApplyTerminalDetail {

	private String brandName;

	@SerializedName("model_number")
	private String modelNumber;

	@SerializedName("serial_num")
	private String serialNumber;

	private String channelName;
	
	private int channelId;
	
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * 支持开通：1对公 2 对私 3 全部
	 * 
	 */
	private int supportRequirementType;
	
	/**
	 * 支持开通：1对公 2 对私 3 全部
	 * 
	 */
	public int getSupportRequirementType() {
		return supportRequirementType;
	}

	public void setSupportRequirementType(int supportRequirementType) {
		this.supportRequirementType = supportRequirementType;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
