package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Qiye {
    /*��ҵ�˺�*/
    private String qiyeUserName;
    public String getQiyeUserName() {
        return qiyeUserName;
    }
    public void setQiyeUserName(String qiyeUserName) {
        this.qiyeUserName = qiyeUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*��ҵ����*/
    private String qiyeName;
    public String getQiyeName() {
        return qiyeName;
    }
    public void setQiyeName(String qiyeName) {
        this.qiyeName = qiyeName;
    }

    /*��ҵ����*/
    private String qiyeQualify;
    public String getQiyeQualify() {
        return qiyeQualify;
    }
    public void setQiyeQualify(String qiyeQualify) {
        this.qiyeQualify = qiyeQualify;
    }

    /*��ҵ����*/
    private QiyeProperty qiyePropertyObj;
    public QiyeProperty getQiyePropertyObj() {
        return qiyePropertyObj;
    }
    public void setQiyePropertyObj(QiyeProperty qiyePropertyObj) {
        this.qiyePropertyObj = qiyePropertyObj;
    }

    /*��ҵ��ҵ*/
    private QiyeProfession qiyeProfessionObj;
    public QiyeProfession getQiyeProfessionObj() {
        return qiyeProfessionObj;
    }
    public void setQiyeProfessionObj(QiyeProfession qiyeProfessionObj) {
        this.qiyeProfessionObj = qiyeProfessionObj;
    }

    /*��ҵ��ģ*/
    private String qiyeScale;
    public String getQiyeScale() {
        return qiyeScale;
    }
    public void setQiyeScale(String qiyeScale) {
        this.qiyeScale = qiyeScale;
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

    /*��ҵ����*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*��ҵ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}