package com.gogo.haobutler.model.video;

import com.gogo.haobutler.model.BaseModel;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/1 0001
 * @function:
 */
public class VideoData extends BaseModel{
    private String nextPageUrl;
    private long currentDate;
    private long nextPublishDate;
    private int count;
    private List<VideoItem> itemList;

    public List<VideoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<VideoItem> itemList) {
        this.itemList = itemList;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }

    public long getNextPublishDate() {
        return nextPublishDate;
    }

    public void setNextPublishDate(long nextPublishDate) {
        this.nextPublishDate = nextPublishDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "nextPageUrl='" + nextPageUrl + '\'' +
                ", currentDate=" + currentDate +
                ", nextPublishDate=" + nextPublishDate +
                ", count=" + count +
                ", itemList=" + itemList +
                '}';
    }
}
