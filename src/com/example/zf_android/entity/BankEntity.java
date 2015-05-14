package com.example.zf_android.entity;

import java.io.Serializable;
import java.util.List;

public class BankEntity {

	private List<Bank> content;

	public List<Bank> getContent() {
		return content;
	}

	public void setContent(List<Bank> content) {
		this.content = content;
	}

	public class Bank implements Serializable {

		private String no;

		private String name;

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
