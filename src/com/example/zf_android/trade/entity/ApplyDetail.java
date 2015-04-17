package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leo on 2015/3/6.
 */
public class ApplyDetail {

	@SerializedName("applyDetails")
	private ApplyTerminalDetail terminalDetail;

	@SerializedName("openingInfos")
	private OpeningInfos openingInfos;
	
	public OpeningInfos getOpeningInfos() {
		return openingInfos;
	}

	public void setOpeningInfos(OpeningInfos openingInfos) {
		this.openingInfos = openingInfos;
	}

	@SerializedName("materialName")
	private List<ApplyMaterial> materials;

	@SerializedName("merchants")
	private List<ApplyChooseItem> merchants;

	
	
	@SerializedName("applyFor")
	private List<ApplyCustomerDetail> customerDetails;

	public ApplyTerminalDetail getTerminalDetail() {
		return terminalDetail;
	}

	public void setTerminalDetail(ApplyTerminalDetail terminalDetail) {
		this.terminalDetail = terminalDetail;
	}

	public List<ApplyMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(List<ApplyMaterial> materials) {
		this.materials = materials;
	}

	public List<ApplyChooseItem> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<ApplyChooseItem> merchants) {
		this.merchants = merchants;
	}

	public List<ApplyCustomerDetail> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<ApplyCustomerDetail> customerDetails) {
		this.customerDetails = customerDetails;
	}
}
