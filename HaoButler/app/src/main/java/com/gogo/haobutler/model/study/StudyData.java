package com.gogo.haobutler.model.study;

import com.gogo.haobutler.model.BaseModel;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class StudyData extends BaseModel{
    public StudyHead head;
    public List<StudyRecommend> list;

    @Override
    public String toString() {
        return "StudyData{" +
                "head=" + head +
                ", list=" + list +
                '}';
    }
}
