package com.example.energo_monitoring.model;

import com.example.energo_monitoring.model.api.DeviceInfo;

public class DeviceTemperatureTransducer extends DeviceInfo {
    private String installationPlace;
    private String values;
    private String measureType;

    public DeviceTemperatureTransducer(String name, int typeId, String installationPlace, String values) {
        super(name, typeId);
        this.installationPlace = installationPlace;
        this.values = values;
    }

    public String getInstallationPlace() {
        return installationPlace;
    }

    public void setInstallationPlace(String installationPlace) {
        this.installationPlace = installationPlace;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    @Override
    public int getFilledState() {
        if(!(installationPlace == null || installationPlace.isEmpty())
                && !(measureType == null || measureType.isEmpty())
                && !(values == null || values.isEmpty()))
            return 2;
        else if(!(installationPlace == null || installationPlace.isEmpty())
                || !(measureType == null || measureType.isEmpty())
                || !(values == null || values.isEmpty()))
            return 1;
        else
            return 0;
    }
}
