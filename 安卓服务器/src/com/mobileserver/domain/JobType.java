package com.mobileserver.domain;

public class JobType {
    /*ְλ����id*/
    private int jobTypeId;
    public int getJobTypeId() {
        return jobTypeId;
    }
    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    /*��������*/
    private String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}