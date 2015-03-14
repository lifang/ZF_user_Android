package com.example.zf_android.entity;

import java.util.List;

public class OrderEntity {
//    {
//        "order_number": "120150312154424229",
	private String order_number;
//        "order_psf": "0",
	private int order_psf;
//        "order_createTime": "2015-03-12 15:44:24",
	private String order_createTime;
//        "order_totalNum": "1",
	private String order_totalNum;
	
public String getOrder_totalNum() {
		return order_totalNum;
	}
	public void setOrder_totalNum(String order_totalNum) {
		this.order_totalNum = order_totalNum;
	}
	//        "order_id": "117",
	private String order_id;
	
//        "order_totalPrice": 35000,
	private int order_totalPrice;
//        "order_status": 4,
	private int order_status;
	private List<Goodlist> order_goodsList;
//        "order_goodsList": [
//            {
//                "good_name": "0312泰山Pos旗舰版",
//                "good_logo": "",
//                "good_brand": "品牌q",
//                "good_price": 35000,
//                "good_volume_number": 5,
//                "good_num": "1",
//                "good_channel": "0312支付通道",
//                "good_id": 22
//            }
//        ],
//        "order_type": "1"
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public int getOrder_psf() {
		return order_psf;
	}
	public void setOrder_psf(int order_psf) {
		this.order_psf = order_psf;
	}
	public String getOrder_createTime() {
		return order_createTime;
	}
	public void setOrder_createTime(String order_createTime) {
		this.order_createTime = order_createTime;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getOrder_totalPrice() {
		return order_totalPrice;
	}
	public void setOrder_totalPrice(int order_totalPrice) {
		this.order_totalPrice = order_totalPrice;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public List<Goodlist> getOrder_goodsList() {
		return order_goodsList;
	}
	public void setOrder_goodsList(List<Goodlist> order_goodsList) {
		this.order_goodsList = order_goodsList;
	}
	
	
	
}
