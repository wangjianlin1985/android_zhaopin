package com.chengxusheji.domain;

import java.sql.Timestamp;
public class QiyeProfession {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*��ҵ����*/
    private String professionName;
    public String getProfessionName() {
        return professionName;
    }
    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

}