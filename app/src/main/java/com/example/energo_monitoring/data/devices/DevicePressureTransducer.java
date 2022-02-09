package com.example.energo_monitoring.data.devices;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.data.api.DeviceInfo;

@Entity
public class DevicePressureTransducer extends DeviceInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int dataId;

    private String installationPlace;
    private String manufacturer;
    private String values;
    private String sensorRange;
    private String comment;

    public DevicePressureTransducer() {
        super("", 0);
    }

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
