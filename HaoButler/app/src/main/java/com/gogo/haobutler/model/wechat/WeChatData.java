package com.gogo.haobutler.model.wechat;

/**
 * @author: 闫昊
 * @date: 2018/6/15 0015
 * @function: 微信精选
 */
public class WeChatData {
    private String title;
    private String source;
    private String firstImg;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WeChatData{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
