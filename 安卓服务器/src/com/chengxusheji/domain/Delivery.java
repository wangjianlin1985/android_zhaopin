package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Delivery {
    /*投递id*/
    private int deliveryId;
    public int getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    /*投递的职位*/
    private Job jobObj;
    public Job getJobObj() {
        return jobObj;
    }
    public void setJobObj(Job jobObj) {
        this.jobObj = jobObj;
    }

    /*投递人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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
    private DeliveryState stateObj;
    public DeliveryState getStateObj() {
        return stateObj;
    }
    public void setStateObj(DeliveryState stateObj) {
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