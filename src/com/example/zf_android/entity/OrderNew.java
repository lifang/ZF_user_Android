package com.example.zf_android.entity;

public class OrderNew {
//	 "order_number": "020150311171727450",
//     "order_receiver_phone": "18352445051",
//     "order_payment_type": "",
//     "order_comment": "",
//     "order_address": "江苏省苏州市工业园区独墅湖高教区",
	private String order_number;
	private String order_receiver_phone;
	private String order_address;
	private String order_payment_type;
	private String order_comment;
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getOrder_receiver_phone() {
		return order_receiver_phone;
	}
	public void setOrder_receiver_phone(String order_receiver_phone) {
		this.order_receiver_phone = order_receiver_phone;
	}
	public String getOrder_address() {
		return order_address;
	}
	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}
	public String getOrder_payment_type() {
		return order_payment_type;
	}
	public void setOrder_payment_type(String order_payment_type) {
		this.order_payment_type = order_payment_type;
	}
	public String getOrder_comment() {
		return order_comment;
	}
	public void setOrder_comment(String order_comment) {
		this.order_comment = order_comment;
	}
}
