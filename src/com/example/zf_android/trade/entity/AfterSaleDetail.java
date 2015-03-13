package com.example.zf_android.trade.entity;

import com.example.zf_android.trade.common.Page;
import com.example.zf_android.trade.common.Pageable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetail {

	private int id;

	private int status;

	@SerializedName("apply_time")
	private String applyTime;

	@SerializedName("terminal_num")
	private String terminalNum;

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("brand_number")
	private String brandNumber;

	@SerializedName("zhifu_pingtai")
	private String payChannel;

	@SerializedName("merchant_name")
	private String merchantName;

	@SerializedName("merchant_phone")
	private String merchantPhone;

	private Pageable<Comment> comments;

	@SerializedName("resource_info")
	private List<ResourceInfo> resourceInfos;

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

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandNumber() {
		return brandNumber;
	}

	public void setBrandNumber(String brandNumber) {
		this.brandNumber = brandNumber;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantPhone() {
		return merchantPhone;
	}

	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}

	public Pageable<Comment> getComments() {
		return comments;
	}

	public void setComments(Pageable<Comment> comments) {
		this.comments = comments;
	}

	public List<ResourceInfo> getResourceInfos() {
		return resourceInfos;
	}

	public void setResourceInfos(List<ResourceInfo> resourceInfos) {
		this.resourceInfos = resourceInfos;
	}
}
