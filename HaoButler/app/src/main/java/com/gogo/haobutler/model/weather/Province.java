package com.gogo.haobutler.model.weather;

import org.litepal.crud.DataSupport;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class Province extends DataSupport {
    private int provinceCode;
    private String name;

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceCode=" + provinceCode +
                ", name='" + name + '\'' +
                '}';
    }
}
