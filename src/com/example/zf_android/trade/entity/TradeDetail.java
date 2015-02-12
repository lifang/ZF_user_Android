package com.example.zf_android.trade.entity;

/**
 * Created by Leo on 2015/2/12.
 */
public class TradeDetail {

    private String tradedTimeStr;
    private int agentId;
    private String terminalNumber;
    private int amount;
    private int payChannelId;
    private String tradeNumber;
    private int profitPrice;
    private int poundage;
    private String batchNumber;
    private String payIntoAccount;
    private String merchantNumber;
    private int tradedStatus;
    private String merchantName;
    private String payFromAccount;
    private String payChannelName;

    public String getTradedTimeStr() {
        return tradedTimeStr;
    }

    public void setTradedTimeStr(String tradedTimeStr) {
        this.tradedTimeStr = tradedTimeStr;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayChannelId() {
        return payChannelId;
    }

    public void setPayChannelId(int payChannelId) {
        this.payChannelId = payChannelId;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public int getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(int profitPrice) {
        this.profitPrice = profitPrice;
    }

    public int getPoundage() {
        return poundage;
    }

    public void setPoundage(int poundage) {
        this.poundage = poundage;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPayIntoAccount() {
        return payIntoAccount;
    }

    public void setPayIntoAccount(String payIntoAccount) {
        this.payIntoAccount = payIntoAccount;
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public int getTradedStatus() {
        return tradedStatus;
    }

    public void setTradedStatus(int tradedStatus) {
        this.tradedStatus = tradedStatus;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPayFromAccount() {
        return payFromAccount;
    }

    public void setPayFromAccount(String payFromAccount) {
        this.payFromAccount = payFromAccount;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }
}
