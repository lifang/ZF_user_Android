package com.example.zf_android.entity;

import java.util.List;

public class PosSelectEntity {
	/**
	 * pos  品牌
	 */
	private List<PosItem> brands;
	/**
	 * pos  类型
	 */
 	private  List<PrePosItem> category;
	public List<PrePosItem> getCategory() {
		return category;
	}







	public void setCategory(List<PrePosItem> category) {
		this.category = category;
	}







	/**
	 * pos  签购单
	 */
	private  List<PosItem> sale_slip;
	/**
	 * pos 支付卡 类型
	 */
	private  List<PosItem> pay_card;
	/**
	 * pos  支付通道
	 */
	private  List<PosItem> pay_channel;	/**
	 * pos  消费类型
	 */
	
	private  List<PosItem> trade_type;
	/**
	 * pos  对账日期
	 */
	private  List<PosItem> tDate;
	
	private int minPrice;
	private int maxPrice;
	
	
	
	
	
	
	public int getMinPrice() {
		return minPrice;
	}







	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}







	public int getMaxPrice() {
		return maxPrice;
	}







	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}







	public List<PosItem> getBrands() {
		return brands;
	}







	public void setBrands(List<PosItem> brands) {
		this.brands = brands;
	}







	public List<PosItem> getSale_slip() {
		return sale_slip;
	}







	public void setSale_slip(List<PosItem> sale_slip) {
		this.sale_slip = sale_slip;
	}







	public List<PosItem> getPay_card() {
		return pay_card;
	}







	public void setPay_card(List<PosItem> pay_card) {
		this.pay_card = pay_card;
	}







	public List<PosItem> getPay_channel() {
		return pay_channel;
	}







	public void setPay_channel(List<PosItem> pay_channel) {
		this.pay_channel = pay_channel;
	}







	public List<PosItem> getTrade_type() {
		return trade_type;
	}







	public void setTrade_type(List<PosItem> trade_type) {
		this.trade_type = trade_type;
	}







	public List<PosItem> gettDate() {
		return tDate;
	}







	public void settDate(List<PosItem> tDate) {
		this.tDate = tDate;
	}







 
}
