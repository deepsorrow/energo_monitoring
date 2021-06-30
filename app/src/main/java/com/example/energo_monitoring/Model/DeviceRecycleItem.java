package com.example.energo_monitoring.Model;

public class DeviceRecycleItem {
    private String name;
    private int typeId;
    private String lastCheckDate;

    public DeviceRecycleItem(String name, int typeId) {
        this.name = name;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(String lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }
}
