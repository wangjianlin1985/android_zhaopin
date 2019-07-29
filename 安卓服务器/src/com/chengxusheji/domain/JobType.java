package com.chengxusheji.domain;

import java.sql.Timestamp;
public class JobType {
    /*职位分类id*/
    private int jobTypeId;
    public int getJobTypeId() {
        return jobTypeId;
    }
    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    /*分类名称*/
    private String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}