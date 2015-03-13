package com.example.zf_android.trade.common;

import java.util.List;

/**
 * Created by Leo on 2015/3/13.
 */
public class Pageable<T> {

	private int total;

	private List<T> content;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
