package com.example.zf_android.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChanelEntitiy implements Serializable{
//    "id": 1,
//    "service_rate": 30,
//    "description": "说明pcbc",
//    "name": "T+1"e 
	private String trade_value;
	private int terminal_rate;
	private int standard_rate;
	
	
	public int getStandard_rate() {
		return standard_rate;
	}
	public void setStandard_rate(int standard_rate) {
		this.standard_rate = standard_rate;
	}
	public int getTerminal_rate() {
		return terminal_rate;
	}
	public void setTerminal_rate(int terminal_rate) {
		this.terminal_rate = terminal_rate;
	}
	public String getTrade_value() {
		return trade_value;
	}
	public void setTrade_value(String trade_value) {
		this.trade_value = trade_value;
	}
	private int id;
	private int service_rate; 
	private String description;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getService_rate() {
		return service_rate;
	}
	public void setService_rate(int service_rate) {
		this.service_rate = service_rate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
