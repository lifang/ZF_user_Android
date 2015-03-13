package com.example.zf_android.trade.common;

import java.util.List;

/**
 * Created by Leo on 2015/2/28.
 */
public class Page<T> {

    private int total;

    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
