package com.example.zf_android.trade.entity;

/**
 * Created by Leo on 2015/2/7.
 */
public class TradeRecord {

    private int amount;
    private String payIntoAccount;
    private String tradedStatus;
    private String tradedTimeStr;
    private String payFromAccount;
    private String terminalNumber;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayIntoAccount() {
        return payIntoAccount;
    }

    public void setPayIntoAccount(String payIntoAccount) {
        this.payIntoAccount = payIntoAccount;
    }

    public String getTradedStatus() {
        return tradedStatus;
    }

    public void setTradedStatus(String tradedStatus) {
        this.tradedStatus = tradedStatus;
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

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }
}
