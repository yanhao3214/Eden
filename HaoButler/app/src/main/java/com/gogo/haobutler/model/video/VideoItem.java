package com.gogo.haobutler.model.video;

/**
 * @author: 闫昊
 * @date: 2018/7/1 0001
 * @function:
 */
public class VideoItem {
    private String title;
    private String category;
    private String description;
    private int collectionCount;
    private int shareCount;
    private int replyCount;
    private String slogan;
    private String authorIcon;
    private String authorName;
    private String authorDesc;
    private String coverUrl;
    private String detailCoverUrl;
    private String playUrl;
    private int duration;
    private String webUrl;
    private int width;
    private int height;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAuthorIcon() {
        return authorIcon;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    public void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDetailCoverUrl() {
        return detailCoverUrl;
    }

    public void setDetailCoverUrl(String detailCoverUrl) {
        this.detailCoverUrl = detailCoverUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", collectionCount=" + collectionCount +
                ", shareCount=" + shareCount +
                ", replyCount=" + replyCount +
                ", slogan='" + slogan + '\'' +
                ", authorIcon='" + authorIcon + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorDesc='" + authorDesc + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", detailCoverUrl='" + detailCoverUrl + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", duration=" + duration +
                ", webUrl='" + webUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
