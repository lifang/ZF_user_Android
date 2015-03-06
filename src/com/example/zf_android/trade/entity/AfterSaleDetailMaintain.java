package com.example.zf_android.trade.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetailMaintain extends AfterSaleDetail {

    @SerializedName("receiver_addr")
    private String receiverAddr;

    @SerializedName("repair_price")
    private String repairPrice;

    private String description;

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public String getRepairPrice() {
        return repairPrice;
    }

    public void setRepairPrice(String repairPrice) {
        this.repairPrice = repairPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
