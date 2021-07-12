package com.example.energo_monitoring.model;

import com.example.energo_monitoring.model.api.DeviceInfo;

public class DeviceFlowTransducer extends DeviceInfo {

    private String installationPlace;
    private String manufacturer;
    private String diameter;
    private String impulseWeight;
    private String values;

    public DeviceFlowTransducer(String name, int typeId) {
        super(name, typeId);
    }

    public DeviceFlowTransducer(String name, int typeId, String lastCheckDate, String installationPlace, String manufacturer, String diameter, String values) {
        super(name, typeId);
        this.installationPlace = installationPlace;
        this.manufacturer = manufacturer;
        this.diameter = diameter;
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

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getImpulseWeight() {
        return impulseWeight;
    }

    public void setImpulseWeight(String impulseWeight) {
        this.impulseWeight = impulseWeight;
    }

    @Override
    public int getFilledState() {
        if(!(installationPlace == null || installationPlace.isEmpty())
                && !(manufacturer == null || manufacturer.isEmpty())
                && !(diameter == null || diameter.isEmpty())
                && !(impulseWeight == null || impulseWeight.isEmpty())
                && !(values == null || values.isEmpty()))
            return 2;
        else if(!(installationPlace == null || installationPlace.isEmpty())
                || !(manufacturer == null || manufacturer.isEmpty())
                || !(diameter == null || diameter.isEmpty())
                || !(impulseWeight == null || impulseWeight.isEmpty())
                || !(values == null || values.isEmpty()))
            return 1;
        else
            return 0;
    }
}
