package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalRate {

    private int id;

    @SerializedName("trade_value")
    private String type;

    @SerializedName("terminal_rate")
    private String terminalRate;

    @SerializedName("service_rate")
    private int serviceRate;

    @SerializedName("base_rate")
    private int baseRate;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTerminalRate() {
        return terminalRate;
    }

    public void setTerminalRate(String terminalRate) {
        this.terminalRate = terminalRate;
    }

    public int getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(int serviceRate) {
        this.serviceRate = serviceRate;
    }

    public int getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(int baseRate) {
        this.baseRate = baseRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
