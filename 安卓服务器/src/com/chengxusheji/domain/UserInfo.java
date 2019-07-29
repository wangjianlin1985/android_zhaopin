package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserInfo {
    /*用户名*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*用户照片*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*学历*/
    private SchoolRecord myShoolRecord;
    public SchoolRecord getMyShoolRecord() {
        return myShoolRecord;
    }
    public void setMyShoolRecord(SchoolRecord myShoolRecord) {
        this.myShoolRecord = myShoolRecord;
    }

    /*毕业学校*/
    private String schoolName;
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /*工作经验*/
    private String workYears;
    public String getWorkYears() {
        return workYears;
    }
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*邮箱*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*现居地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*求职意向*/
    private String qzyx;
    public String getQzyx() {
        return qzyx;
    }
    public void setQzyx(String qzyx) {
        this.qzyx = qzyx;
    }

    /*工作经历*/
    private String gzjl;
    public String getGzjl() {
        return gzjl;
    }
    public void setGzjl(String gzjl) {
        this.gzjl = gzjl;
    }

    /*教育经历*/
    private String jyjl;
    public String getJyjl() {
        return jyjl;
    }
    public void setJyjl(String jyjl) {
        this.jyjl = jyjl;
    }

    /*自我评价*/
    private String zwpj;
    public String getZwpj() {
        return zwpj;
    }
    public void setZwpj(String zwpj) {
        this.zwpj = zwpj;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}