package com.gogo.haobutler.model.weather;

import org.litepal.crud.DataSupport;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class City extends DataSupport {
    private int cityCode;
    private String name;
    private int provinceId;

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityCode=" + cityCode +
                ", name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
}
