package com.example.energo_monitoring.model;

import com.example.energo_monitoring.model.api.DeviceInfo;

public class DevicePressureTransducer extends DeviceInfo {
    private String installationPlace;
    private String manufacturer;
    private String values;
    private String sensorRange;

    public DevicePressureTransducer(String name, int typeId, String installationPlace, String manufacturer, String values) {
        super(name, typeId);
        this.installationPlace = installationPlace;
        this.manufacturer = manufacturer;
        this.values = values;
    }

    public String getInstallationPlace() {
        return installationPlace;
    }

    public void setInstallationPlace(String installationPlace) {
        this.installationPlace = installationPlace;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getSensorRange() {
        return sensorRange;
    }

    public void setSensorRange(String sensorRange) {
        this.sensorRange = sensorRange;
    }

    @Override
    public int getFilledState() {
        if(!(installationPlace == null || installationPlace.isEmpty())
                && !(manufacturer == null || manufacturer.isEmpty())
                && !(sensorRange == null || sensorRange.isEmpty())
                && !(values == null || values.isEmpty()))
            return 2;
        else if(!(installationPlace == null || installationPlace.isEmpty())
                || !(manufacturer == null || manufacturer.isEmpty())
                || !(sensorRange == null || sensorRange.isEmpty())
                || !(values == null || values.isEmpty()))
            return 1;
        else
            return 0;
    }
}
