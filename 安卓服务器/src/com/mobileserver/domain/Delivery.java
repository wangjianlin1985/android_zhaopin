package com.mobileserver.domain;

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
    private int jobObj;
    public int getJobObj() {
        return jobObj;
    }
    public void setJobObj(int jobObj) {
        this.jobObj = jobObj;
    }

    /*Ͷ����*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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
    private int stateObj;
    public int getStateObj() {
        return stateObj;
    }
    public void setStateObj(int stateObj) {
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