package com.example.zf_android.entity;

public class Answer {
	//"json":[{"good_id":1,"customer_id":80,"score":50,"content":"哈哈1"},
	private int good_id;
	private int customer_id;
	private int score;
	private String content;
	public int getGood_id() {
		return good_id;
	}
	public void setGood_id(int good_id) {
		this.good_id = good_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
	
	
}
