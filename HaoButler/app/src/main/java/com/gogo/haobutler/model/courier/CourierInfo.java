package com.gogo.haobutler.model.courier;

/**
 * @author: 闫昊
 * @date: 2018/5/31 0031
 * @function:
 */
public class CourierInfo {
    private String com;
    private String num;
    private String datetime;
    private String remark;
    private String zone;

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierInfo{" +
                "com='" + com + '\'' +
                ", num='" + num + '\'' +
                ", datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
