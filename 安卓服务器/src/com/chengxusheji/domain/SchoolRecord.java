package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SchoolRecord {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*学历名称*/
    private String schooRecordName;
    public String getSchooRecordName() {
        return schooRecordName;
    }
    public void setSchooRecordName(String schooRecordName) {
        this.schooRecordName = schooRecordName;
    }

}