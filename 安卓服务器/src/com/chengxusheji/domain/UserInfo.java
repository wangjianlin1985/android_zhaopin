package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*��������*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*�û���Ƭ*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*ѧ��*/
    private SchoolRecord myShoolRecord;
    public SchoolRecord getMyShoolRecord() {
        return myShoolRecord;
    }
    public void setMyShoolRecord(SchoolRecord myShoolRecord) {
        this.myShoolRecord = myShoolRecord;
    }

    /*��ҵѧУ*/
    private String schoolName;
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /*��������*/
    private String workYears;
    public String getWorkYears() {
        return workYears;
    }
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*����*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*�־ӵ�ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*��ְ����*/
    private String qzyx;
    public String getQzyx() {
        return qzyx;
    }
    public void setQzyx(String qzyx) {
        this.qzyx = qzyx;
    }

    /*��������*/
    private String gzjl;
    public String getGzjl() {
        return gzjl;
    }
    public void setGzjl(String gzjl) {
        this.gzjl = gzjl;
    }

    /*��������*/
    private String jyjl;
    public String getJyjl() {
        return jyjl;
    }
    public void setJyjl(String jyjl) {
        this.jyjl = jyjl;
    }

    /*��������*/
    private String zwpj;
    public String getZwpj() {
        return zwpj;
    }
    public void setZwpj(String zwpj) {
        this.zwpj = zwpj;
    }

    /*ע��ʱ��*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}