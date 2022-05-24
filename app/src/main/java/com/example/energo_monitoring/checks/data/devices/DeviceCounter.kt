package com.example.energo_monitoring.checks.data.devices;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.checks.data.api.DeviceInfo;

@Entity
public class DeviceCounter extends DeviceInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int dataId;

    private String diameter;
    private String impulseWeight;

    public DeviceCounter() {
        super("", 0);
    }

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
