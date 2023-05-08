package com.mobileclient.domain;

import java.io.Serializable;

public class JobWant implements Serializable {
    /*记录id*/
    private int wantId;
    public int getWantId() {
        return wantId;
    }
    public void setWantId(int wantId) {
        this.wantId = wantId;
    }

    /*职位分类*/
    private int jobTypeObj;
    public int getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(int jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*求职专业*/
    private int specialObj;
    public int getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(int specialObj) {
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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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