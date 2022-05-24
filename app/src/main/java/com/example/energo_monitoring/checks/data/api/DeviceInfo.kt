package com.example.energo_monitoring.checks.data.api;

public class DeviceInfo {
    private String deviceName;
    private String deviceNumber;
    private int typeId;
    private String lastCheckDate;

    public DeviceInfo(String deviceName, int typeId) {
        this.deviceName = deviceName;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return deviceName;
    }

    public void setName(String name) {
        this.deviceName = name;
    }

    public String getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(String lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public int getFilledState(){ return 0; };
}
