package com.mobileserver.domain;

public class News {
    /*����id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*���ŷ���*/
    private int newsClassObj;
    public int getNewsClassObj() {
        return newsClassObj;
    }
    public void setNewsClassObj(int newsClassObj) {
        this.newsClassObj = newsClassObj;
    }

    /*����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*����ʱ��*/
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}