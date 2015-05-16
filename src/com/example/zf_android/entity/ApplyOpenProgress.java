package com.example.zf_android.entity;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leo on 2015/3/13.
 */
public class ApplyOpenProgress {

	private int id;
	@SerializedName("serial_num")
	private String terminalNumber;

	private String error;
	private List<OpenStatus> openStatus;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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

	public List<OpenStatus> getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(List<OpenStatus> openStatus) {
		this.openStatus = openStatus;
	}

	public class OpenStatus {
		@SerializedName("trade_value")
		public String tradeValue;
		public int status;
	}
}
