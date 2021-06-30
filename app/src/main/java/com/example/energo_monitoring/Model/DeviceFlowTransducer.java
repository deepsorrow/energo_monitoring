package com.example.energo_monitoring.Model;

import com.example.energo_monitoring.Model.DeviceRecycleItem;

public class DeviceFlowTransducer extends DeviceRecycleItem {

    private String installationPlace;
    private String manufacturer;
    private String diameter;
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
}
