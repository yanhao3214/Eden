package com.gogo.haobutler.model.image;

/**
 * @author: 闫昊
 * @date: 2018/7/5 0005
 * @function:
 */
public class Gank {
    private String date;
    private String url;
    private String author;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Gank{" +
                "date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
