package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Job {
    /*ְλid*/
    private int jobId;
    public int getJobId() {
        return jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    /*��Ƹ��ҵ*/
    private Qiye qiyeObj;
    public Qiye getQiyeObj() {
        return qiyeObj;
    }
    public void setQiyeObj(Qiye qiyeObj) {
        this.qiyeObj = qiyeObj;
    }

    /*��Ƹְλ*/
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /*ְλ����*/
    private JobType jobTypeObj;
    public JobType getJobTypeObj() {
        return jobTypeObj;
    }
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }

    /*����רҵ*/
    private SpecialInfo specialObj;
    public SpecialInfo getSpecialObj() {
        return specialObj;
    }
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }

    /*��Ƹ����*/
    private String personNum;
    public String getPersonNum() {
        return personNum;
    }
    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    /*���ڳ���*/
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    /*н�ʴ���*/
    private String salary;
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /*ѧ��Ҫ��*/
    private SchoolRecord schoolRecordObj;
    public SchoolRecord getSchoolRecordObj() {
        return schoolRecordObj;
    }
    public void setSchoolRecordObj(SchoolRecord schoolRecordObj) {
        this.schoolRecordObj = schoolRecordObj;
    }

    /*��������*/
    private String workYears;
    public String getWorkYears() {
        return workYears;
    }
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /*������ַ*/
    private String workAddress;
    public String getWorkAddress() {
        return workAddress;
    }
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    /*��������*/
    private String welfare;
    public String getWelfare() {
        return welfare;
    }
    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    /*��λҪ��*/
    private String positionDesc;
    public String getPositionDesc() {
        return positionDesc;
    }
    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    /*��ϵ��*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}