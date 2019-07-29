package com.chengxusheji.domain;

import java.sql.Timestamp;
public class News {
    /*新闻id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*新闻分类*/
    private NewsClass newsClassObj;
    public NewsClass getNewsClassObj() {
        return newsClassObj;
    }
    public void setNewsClassObj(NewsClass newsClassObj) {
        this.newsClassObj = newsClassObj;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*新闻内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*发布时间*/
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}