package com.example.zf_android.trade.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2015/2/11.
 */
public class Province implements Serializable {
    private int id;
    private String name;
    private List<City> cities;

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (!(o instanceof Province)) return false;
        Province that = (Province) o;
        return that.getId() == this.getId();
    }

    @Override
    public String toString() {
        return name;
    }
}
