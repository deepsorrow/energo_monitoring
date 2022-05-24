package com.example.energo_monitoring.checks.data.devices;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.checks.data.api.DeviceInfo;

@Entity
public class DeviceTemperatureTransducer extends DeviceInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int dataId;

    private String installationPlace;
    private String values;
    private String length;
    private String comment;

    public DeviceTemperatureTransducer() {
        super("", 0);
    }

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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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
                && !(length == null || length.isEmpty())
                && !(values == null || values.isEmpty()))
            return 2;
        else if(!(installationPlace == null || installationPlace.isEmpty())
                || !(length == null || length.isEmpty())
                || !(values == null || values.isEmpty()))
            return 1;
        else
            return 0;
    }
}
