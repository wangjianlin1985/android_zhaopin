package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Delivery {
    /*Ͷ��id*/
    private int deliveryId;
    public int getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    /*Ͷ�ݵ�ְλ*/
    private Job jobObj;
    public Job getJobObj() {
        return jobObj;
    }
    public void setJobObj(Job jobObj) {
        this.jobObj = jobObj;
    }

    /*Ͷ����*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*Ͷ��ʱ��*/
    private String deliveryTime;
    public String getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /*Ͷ��״̬*/
    private DeliveryState stateObj;
    public DeliveryState getStateObj() {
        return stateObj;
    }
    public void setStateObj(DeliveryState stateObj) {
        this.stateObj = stateObj;
    }

    /*��ҵ�ظ�*/
    private String deliveryDemo;
    public String getDeliveryDemo() {
        return deliveryDemo;
    }
    public void setDeliveryDemo(String deliveryDemo) {
        this.deliveryDemo = deliveryDemo;
    }

}