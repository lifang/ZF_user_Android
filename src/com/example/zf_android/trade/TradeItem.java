package com.example.zf_android.trade;

/**
 * Created by Leo on 2015/2/7.
 */
public class TradeItem {

    /**
     * 交易时间
     */
    private String tradeTime;
    /**
     * 付款账号
     */
    private String tradeAccount;
    /**
     * 收款账号
     */
    private String tradeReceiveAccount;
    /**
     * 终端号
     */
    private String tradeClientNumber;
    /**
     * 交易金额
     */
    private Float tradeAmount;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getTradeReceiveAccount() {
        return tradeReceiveAccount;
    }

    public void setTradeReceiveAccount(String tradeReceiveAccount) {
        this.tradeReceiveAccount = tradeReceiveAccount;
    }

    public String getTradeClientNumber() {
        return tradeClientNumber;
    }

    public void setTradeClientNumber(String tradeClientNumber) {
        this.tradeClientNumber = tradeClientNumber;
    }

    public Float getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Float tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
}
