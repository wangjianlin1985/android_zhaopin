package com.chengxusheji.domain;

import java.sql.Timestamp;
public class NewsClass {
    /*新闻分类id*/
    private int newsClassId;
    public int getNewsClassId() {
        return newsClassId;
    }
    public void setNewsClassId(int newsClassId) {
        this.newsClassId = newsClassId;
    }

    /*新闻分类名称*/
    private String newsClassName;
    public String getNewsClassName() {
        return newsClassName;
    }
    public void setNewsClassName(String newsClassName) {
        this.newsClassName = newsClassName;
    }

}