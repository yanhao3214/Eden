package com.gogo.haobutler.model.study;

import com.gogo.haobutler.model.BaseModel;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class StudyHead extends BaseModel {
    public List<String> ads;
    public List<String> middle;
    public List<StudyHeadFooterItem> footer;

    @Override
    public String toString() {
        return "StudyHead{" +
                "ads=" + ads +
                ", middle=" + middle +
                ", footer=" + footer +
                '}';
    }
}
