package com.example.energo_monitoring.model;

import com.example.energo_monitoring.model.api.DeviceInfo;

public class DeviceCounter extends DeviceInfo {
    private String diameter;
    private String impulseWeight;

    public DeviceCounter(String name, int typeId, String diameter, String impulseWeight) {
        super(name, typeId);
        this.diameter = diameter;
        this.impulseWeight = impulseWeight;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getImpulseWeight() {
        return impulseWeight;
    }

    public void setImpulseWeight(String impulseWeight) {
        this.impulseWeight = impulseWeight;
    }
}
