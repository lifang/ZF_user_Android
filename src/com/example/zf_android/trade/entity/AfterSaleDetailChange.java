package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetailChange extends AfterSaleDetail {

    @SerializedName("receiver_addr")
    private String receiverAddr;

    @SerializedName("change_reason")
    private String changeReason;

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}
