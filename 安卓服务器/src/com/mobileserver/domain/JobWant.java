package com.mobileserver.domain;

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
    private int jobTypeObj;
    public int getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(int jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*��ְרҵ*/
    private int specialObj;
    public int getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(int specialObj) {
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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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