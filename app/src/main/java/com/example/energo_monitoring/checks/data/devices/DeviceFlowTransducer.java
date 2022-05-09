package com.example.energo_monitoring.checks.data.devices;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.checks.data.api.DeviceInfo;

import java.io.Serializable;

@Entity
public class DeviceFlowTransducer extends DeviceInfo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int dataId;

    private String installationPlace;
    private String manufacturer;
    private String diameter;
    private String impulseWeight;
    private String values;
    private String comment;

    public DeviceFlowTransducer() {
        super("", 0);
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
