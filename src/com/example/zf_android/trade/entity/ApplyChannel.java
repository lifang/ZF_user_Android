package com.example.zf_android.trade.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyChannel implements Serializable {

	private int id;
	private String name;
	private List<Billing> billings = new ArrayList<Billing>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Billing> getBillings() {
		return billings;
	}

	public void setBillings(List<Billing> billings) {
		this.billings = billings;
	}

	public class Billing implements Serializable {
		public int id;
		public String name;
	}
}
