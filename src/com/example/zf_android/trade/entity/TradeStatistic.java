package com.example.zf_android.trade.entity;

/**
 * Created by Leo on 2015/2/11.
 */
public class TradeStatistic {

    private int tradeTypeId;
    private int amountTotal;
    private int payChannelId;
    private String payChannelName;
    private String terminalNumber;
    private int tradeTotal;

    public int getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public int getPayChannelId() {
        return payChannelId;
    }

    public void setPayChannelId(int payChannelId) {
        this.payChannelId = payChannelId;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public int getTradeTotal() {
        return tradeTotal;
    }

    public void setTradeTotal(int tradeTotal) {
        this.tradeTotal = tradeTotal;
    }
}
