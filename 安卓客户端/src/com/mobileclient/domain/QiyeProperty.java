package com.mobileclient.domain;

import java.io.Serializable;

public class QiyeProperty implements Serializable {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*企业性质名称*/
    private String propertyName;
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}