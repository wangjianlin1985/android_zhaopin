package com.mobileclient.domain;

import java.io.Serializable;

public class SpecialInfo implements Serializable {
    /*专业id*/
    private int specialId;
    public int getSpecialId() {
        return specialId;
    }
    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    /*专业名称*/
    private String specialName;
    public String getSpecialName() {
        return specialName;
    }
    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

}