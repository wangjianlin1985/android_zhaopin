package com.mobileclient.domain;

import java.io.Serializable;

public class QiyeProfession implements Serializable {
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