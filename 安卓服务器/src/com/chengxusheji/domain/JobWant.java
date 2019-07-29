package com.chengxusheji.domain;

import java.sql.Timestamp;
public class JobWant {
    /*记录id*/
    private int wantId;
    public int getWantId() {
        return wantId;
    }
    public void setWantId(int wantId) {
        this.wantId = wantId;
    }

    /*职位分类*/
    private JobType jobTypeObj;
    public JobType getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*求职专业*/
    private SpecialInfo specialObj;
    public SpecialInfo getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }

    /*职位名称*/
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /*期望薪资*/
    private String salary;
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /*工作地点*/
    private String workCity;
    public String getWorkCity() {
        return workCity;
    }
    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    /*备注说明*/
    private String wantMemo;
    public String getWantMemo() {
        return wantMemo;
    }
    public void setWantMemo(String wantMemo) {
        this.wantMemo = wantMemo;
    }

    /*求职人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}