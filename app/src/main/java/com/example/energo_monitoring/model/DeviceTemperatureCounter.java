package com.example.energo_monitoring.model;

import com.example.energo_monitoring.model.api.DeviceInfo;

public class DeviceTemperatureCounter extends DeviceInfo {

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

    @Override
    public int getFilledState() {
        if(!(unitSystem == null || unitSystem.isEmpty())
                && !(modification == null || modification.isEmpty())
                && !(interval == null || interval.isEmpty()))
            return 2;
        else if(!(unitSystem == null || unitSystem.isEmpty())
                || !(modification == null || modification.isEmpty())
                || !(interval == null || interval.isEmpty()))
            return 1;
        else
            return 0;
    }
}
