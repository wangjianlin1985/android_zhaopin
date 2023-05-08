package com.chengxusheji.domain;

import java.sql.Timestamp;
public class QiyeProfession {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*行业名称*/
    private String professionName;
    public String getProfessionName() {
        return professionName;
    }
    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

}