package com.example.zf_android.entity;

public class JifenEntity {
//    "order_number": "020150206134248779",
//    "payedAt": "2015-02-08 20:45:09",
//    "quantity": 20,
//    "types": 1,
//    "target_type": 1,
//    "actual_price": 408
	//{"moneyTotal":8800,"quantityTotal":88}}
	private int moneyTotal;
	private int quantityTotal;
	
	
	
	
	public int getMoneyTotal() {
		return moneyTotal;
	}
	public void setMoneyTotal(int moneyTotal) {
		this.moneyTotal = moneyTotal;
	}
	public int getQuantityTotal() {
		return quantityTotal;
	}
	public void setQuantityTotal(int quantityTotal) {
		this.quantityTotal = quantityTotal;
	}
	private String order_number;
	private String payedAt;
	private int quantity;
	private int types;
	private int target_type;
	private String actual_price;
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getPayedAt() {
		return payedAt;
	}
	public void setPayedAt(String payedAt) {
		this.payedAt = payedAt;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public int getTarget_type() {
		return target_type;
	}
	public void setTarget_type(int target_type) {
		this.target_type = target_type;
	}
	public String getActual_price() {
		return actual_price;
	}
	public void setActual_price(String actual_price) {
		this.actual_price = actual_price;
	}
	
}
