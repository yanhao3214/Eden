package com.gogo.haobutler.model.study;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class Study {
    public String ecode;
    public String emsg;
    public StudyData data;

    @Override
    public String toString() {
        return "Study{" +
                "ecode='" + ecode + '\'' +
                ", emsg='" + emsg + '\'' +
                ", data=" + data +
                '}';
    }
}
