package com.gogo.haobutler.model.phone;

/**
 * @author: 闫昊
 * @date: 2018/5/23 0023
 * @function:
 */
public class PhoneData {
    private String number;
    private String province;
    private String carrier;
    private String ownerCarrier;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getOwnerCarrier() {
        return ownerCarrier;
    }

    public void setOwnerCarrier(String ownerCarrier) {
        this.ownerCarrier = ownerCarrier;
    }

    @Override
    public String toString() {
        return "PhoneData{" +
                "number='" + number + '\'' +
                ", province='" + province + '\'' +
                ", carrier='" + carrier + '\'' +
                ", ownerCarrier='" + ownerCarrier + '\'' +
                '}';
    }
}
