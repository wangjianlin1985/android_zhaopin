package com.mobileclient.domain;

import java.io.Serializable;

public class Delivery implements Serializable {
    /*投递id*/
    private int deliveryId;
    public int getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    /*投递的职位*/
    private int jobObj;
    public int getJobObj() {
        return jobObj;
    }
    public void setJobObj(int jobObj) {
        this.jobObj = jobObj;
    }

    /*投递人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*投递时间*/
    private String deliveryTime;
    public String getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /*投递状态*/
    private int stateObj;
    public int getStateObj() {
        return stateObj;
    }
    public void setStateObj(int stateObj) {
        this.stateObj = stateObj;
    }

    /*企业回复*/
    private String deliveryDemo;
    public String getDeliveryDemo() {
        return deliveryDemo;
    }
    public void setDeliveryDemo(String deliveryDemo) {
        this.deliveryDemo = deliveryDemo;
    }

}