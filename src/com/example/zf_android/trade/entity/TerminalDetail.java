package com.example.zf_android.trade.entity;

import java.util.List;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalDetail {

	private TerminalApply applyDetails;

	private TerminalTenancy tenancy;

	private List<TerminalOpen> openingDetails;

	private List<TerminalComment> trackRecord;

	private List<TerminalRate> rates;

	public TerminalApply getApplyDetails() {
		return applyDetails;
	}

	public void setApplyDetails(TerminalApply applyDetails) {
		this.applyDetails = applyDetails;
	}

	public TerminalTenancy getTenancy() {
		return tenancy;
	}

	public void setTenancy(TerminalTenancy tenancy) {
		this.tenancy = tenancy;
	}

	public List<TerminalOpen> getOpeningDetails() {
		return openingDetails;
	}

	public void setOpeningDetails(List<TerminalOpen> openingDetails) {
		this.openingDetails = openingDetails;
	}

	public List<TerminalComment> getTrackRecord() {
		return trackRecord;
	}

	public void setTrackRecord(List<TerminalComment> trackRecord) {
		this.trackRecord = trackRecord;
	}

	public List<TerminalRate> getRates() {
		return rates;
	}

	public void setRates(List<TerminalRate> rates) {
		this.rates = rates;
	}
}
