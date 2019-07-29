package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Job {
    /*职位id*/
    private int jobId;
    public int getJobId() {
        return jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    /*招聘企业*/
    private Qiye qiyeObj;
    public Qiye getQiyeObj() {
        return qiyeObj;
    }
    public void setQiyeObj(Qiye qiyeObj) {
        this.qiyeObj = qiyeObj;
    }

    /*招聘职位*/
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /*职位分类*/
    private JobType jobTypeObj;
    public JobType getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*所属专业*/
    private SpecialInfo specialObj;
    public SpecialInfo getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }

    /*招聘人数*/
    private String personNum;
    public String getPersonNum() {
        return personNum;
    }
    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    /*所在城市*/
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    /*薪资待遇*/
    private String salary;
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /*学历要求*/
    private SchoolRecord schoolRecordObj;
    public SchoolRecord getSchoolRecordObj() {
        return schoolRecordObj;
    }
    public void setSchoolRecordObj(SchoolRecord schoolRecordObj) {
        this.schoolRecordObj = schoolRecordObj;
    }

    /*工作年限*/
    private String workYears;
    public String getWorkYears() {
        return workYears;
    }
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /*工作地址*/
    private String workAddress;
    public String getWorkAddress() {
        return workAddress;
    }
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    /*福利待遇*/
    private String welfare;
    public String getWelfare() {
        return welfare;
    }
    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    /*岗位要求*/
    private String positionDesc;
    public String getPositionDesc() {
        return positionDesc;
    }
    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    /*联系人*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}