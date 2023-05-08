package com.mobileclient.domain;

import java.io.Serializable;

public class DeliveryState implements Serializable {
    /*投递状态id*/
    private int stateId;
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /*投递状态*/
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}