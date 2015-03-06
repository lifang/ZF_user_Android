package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/2/7.
 */
public class TradeRecord {

	private int id;
	private int amount;
	private int tradedStatus;
	private String terminalNumber;
	private String tradedTimeStr;

	// TRANSFER
	// REPAYMENT
	private String payFromAccount;
	private String payIntoAccount;

	// CONSUME
	private String payedTimeStr;
	private int poundage;

	// LIFE_PAY
	@SerializedName("account_name")
	private String accountName;

	@SerializedName("account_number")
	private String accountNumber;

	// PHONE_PAY
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTradedStatus() {
		return tradedStatus;
	}

	public void setTradedStatus(int tradedStatus) {
		this.tradedStatus = tradedStatus;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public String getTradedTimeStr() {
		return tradedTimeStr;
	}

	public void setTradedTimeStr(String tradedTimeStr) {
		this.tradedTimeStr = tradedTimeStr;
	}

	public String getPayFromAccount() {
		return payFromAccount;
	}

	public void setPayFromAccount(String payFromAccount) {
		this.payFromAccount = payFromAccount;
	}

	public String getPayIntoAccount() {
		return payIntoAccount;
	}

	public void setPayIntoAccount(String payIntoAccount) {
		this.payIntoAccount = payIntoAccount;
	}

	public String getPayedTimeStr() {
		return payedTimeStr;
	}

	public void setPayedTimeStr(String payedTimeStr) {
		this.payedTimeStr = payedTimeStr;
	}

	public int getPoundage() {
		return poundage;
	}

	public void setPoundage(int poundage) {
		this.poundage = poundage;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
