package com.gogo.haobutler.model.study;

import com.gogo.haobutler.model.BaseModel;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class StudyRecommend extends BaseModel {
    public int type;
    public String logo;
    public String title;
    public String info;
    public String price;
    public String text;
    public String site;
    public String from;
    public String zan;
    public List<String> url;

    @Override
    public String toString() {
        return "StudyRecommend{" +
                "type=" + type +
                ", logo='" + logo + '\'' +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", price='" + price + '\'' +
                ", text='" + text + '\'' +
                ", site='" + site + '\'' +
                ", from='" + from + '\'' +
                ", zan='" + zan + '\'' +
                ", url=" + url +
                '}';
    }
}
