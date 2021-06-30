package com.example.energo_monitoring.Model;

public class DeviceTemperatureCounter extends DeviceRecycleItem {

    private String unitSystem;
    private String modification;
    private String interval;

    public DeviceTemperatureCounter(String name, int typeId) {
        super(name, typeId);
    }

    public DeviceTemperatureCounter(String name, int typeId, String lastCheckDate, String unitSystem, String modification, String interval) {
        super(name, typeId);

        this.unitSystem = unitSystem;
        this.modification = modification;
        this.interval = interval;
    }

    public String getUnitSystem() {
        return unitSystem;
    }

    public void setUnitSystem(String unitSystem) {
        this.unitSystem = unitSystem;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
