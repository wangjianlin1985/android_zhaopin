package com.chengxusheji.domain;

import java.sql.Timestamp;
public class DeliveryState {
    /*Ͷ��״̬id*/
    private int stateId;
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /*Ͷ��״̬*/
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}