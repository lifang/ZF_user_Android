package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetailLease extends AfterSaleDetail {

	@SerializedName("lease_price")
	private int leasePrice;

	@SerializedName("lease_deposit")
	private int leaseDeposit;

	@SerializedName("return_price")
	private int return_price;
	
	@SerializedName("lease_length")
	private int leaseLength;

	@SerializedName("lease_max_time")
	private int leaseMaxTime;

	@SerializedName("lease_min_time")
	private int leaseMinTime;

	@SerializedName("receiver_name")
	private String receiverName;

	@SerializedName("receiver_phone")
	private String receiverPhone;

	private String crf_retrun_price;
	
	public String getCrf_retrun_price() {
		return crf_retrun_price;
	}

	public void setCrf_retrun_price(String crf_retrun_price) {
		this.crf_retrun_price = crf_retrun_price;
	}
	
	public int getReturn_price() {
		return return_price;
	}

	public void setReturn_price(int return_price) {
		this.return_price = return_price;
	}

	public int getLeasePrice() {
		return leasePrice;
	}

	public void setLeasePrice(int leasePrice) {
		this.leasePrice = leasePrice;
	}

	public int getLeaseDeposit() {
		return leaseDeposit;
	}

	public void setLeaseDeposit(int leaseDeposit) {
		this.leaseDeposit = leaseDeposit;
	}

	public int getLeaseLength() {
		return leaseLength;
	}

	public void setLeaseLength(int leaseLength) {
		this.leaseLength = leaseLength;
	}

	public int getLeaseMaxTime() {
		return leaseMaxTime;
	}

	public void setLeaseMaxTime(int leaseMaxTime) {
		this.leaseMaxTime = leaseMaxTime;
	}

	public int getLeaseMinTime() {
		return leaseMinTime;
	}

	public void setLeaseMinTime(int leaseMinTime) {
		this.leaseMinTime = leaseMinTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
}
