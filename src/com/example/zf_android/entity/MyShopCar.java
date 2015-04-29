package com.example.zf_android.entity;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class MyShopCar {
	private int code;
	private String message;
	private List<Good> result;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public static class Good {
		private boolean isChecked;
		private int id;
		private String title;
		private String url_path;
		private String name;
		private int goodId;
		private int quantity;
		private int retail_price;
		private String Model_number;
		private int opening_cost;
		
		
		public int getOpening_cost() {
			return opening_cost;
		}

		public void setOpening_cost(int opening_cost) {
			this.opening_cost = opening_cost;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl_path() {
			return url_path;
		}

		public void setUrl_path(String url_path) {
			this.url_path = url_path;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getGoodId() {
			return goodId;
		}

		public void setGoodId(int goodId) {
			this.goodId = goodId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public int getRetail_price() {
			return retail_price;
		}

		public void setRetail_price(int retail_price) {
			this.retail_price = retail_price;
		}

		public String getModel_number() {
			return Model_number;
		}

		public void setModel_number(String model_number) {
			Model_number = model_number;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

	}

	public static MyShopCar getShopCar(String json) {
		if (isGoodJson(json)) {
			try {
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				return gson.fromJson(json, MyShopCar.class);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isGoodJson(String json) {
		if (json != null && json.isEmpty()) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}

	public List<Good> getResult() {
		return result;
	}

	public void setResult(List<Good> result) {
		this.result = result;
	}
}
