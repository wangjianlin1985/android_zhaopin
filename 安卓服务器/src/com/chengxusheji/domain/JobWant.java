package com.chengxusheji.domain;

import java.sql.Timestamp;
public class JobWant {
    /*��¼id*/
    private int wantId;
    public int getWantId() {
        return wantId;
    }
    public void setWantId(int wantId) {
        this.wantId = wantId;
    }

    /*ְλ����*/
    private JobType jobTypeObj;
    public JobType getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*��ְרҵ*/
    private SpecialInfo specialObj;
    public SpecialInfo getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }

    /*ְλ����*/
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /*����н��*/
    private String salary;
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /*�����ص�*/
    private String workCity;
    public String getWorkCity() {
        return workCity;
    }
    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    /*��ע˵��*/
    private String wantMemo;
    public String getWantMemo() {
        return wantMemo;
    }
    public void setWantMemo(String wantMemo) {
        this.wantMemo = wantMemo;
    }

    /*��ְ��*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}