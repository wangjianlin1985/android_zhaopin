package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SchoolRecord {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*ѧ������*/
    private String schooRecordName;
    public String getSchooRecordName() {
        return schooRecordName;
    }
    public void setSchooRecordName(String schooRecordName) {
        this.schooRecordName = schooRecordName;
    }

}