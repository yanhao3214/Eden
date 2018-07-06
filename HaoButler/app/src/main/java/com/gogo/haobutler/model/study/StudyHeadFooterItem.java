package com.gogo.haobutler.model.study;

import com.gogo.haobutler.model.BaseModel;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class StudyHeadFooterItem extends BaseModel {
    public String title;
    public String info;
    public String from;
    public String imageOne;
    public String imageTwo;
    public String destationUrl;

    @Override
    public String toString() {
        return "StudyHeadFooterItem{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", from='" + from + '\'' +
                ", imageOne='" + imageOne + '\'' +
                ", imageTwo='" + imageTwo + '\'' +
                ", destationUrl='" + destationUrl + '\'' +
                '}';
    }
}
