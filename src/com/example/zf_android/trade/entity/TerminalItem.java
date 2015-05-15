package com.example.zf_android.trade.entity;

import android.R.integer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalItem {

	private int id;
	@SerializedName("serial_num")
	private String terminalNumber;
	private int status;
	private String type;
	private String appid;
	private String openingProtocol;
	private int hasVideoVerify;
	private String openstatus;

	public String getOpenstatus() {
		return openstatus;
	}

	public void setOpenstatus(String openstatus) {
		this.openstatus = openstatus;
	}

	public int getHasVideoVerify() {
		return hasVideoVerify;
	}

	public void setHasVideoVerify(int hasVideoVerify) {
		this.hasVideoVerify = hasVideoVerify;
	}

	public String getOpeningProtocol() {
		return openingProtocol;
	}

	public void setOpeningProtocol(String openingProtocol) {
		this.openingProtocol = openingProtocol;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
