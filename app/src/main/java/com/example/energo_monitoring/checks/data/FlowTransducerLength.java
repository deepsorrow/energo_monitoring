package com.example.energo_monitoring.checks.data;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer;

import java.util.ArrayList;

@Entity
public class FlowTransducerLength {
    @PrimaryKey
    public int id;

    public int dataId;

    private String lengthBefore = "";
    private String lengthAfter = "";

    @Ignore
    private ArrayList<Bitmap> photos;

    @Ignore
    public String photosBase64;

    public String photosString = "";

    @Ignore
    public DeviceFlowTransducer device;
    public int deviceOrder;
    public int icon;

    public FlowTransducerLength() {
    }

    public FlowTransducerLength(int id, DeviceFlowTransducer device, int dataId) {
        this.id = id;
        this.device = device;
        this.deviceOrder = id;
        this.icon = R.drawable.ic_baseline_trip_origin_24;
        this.dataId = dataId;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public void setLengthAfter(String lengthAfter) {
        this.lengthAfter = lengthAfter;
    }

    public void setPhotos(ArrayList<Bitmap> photos) {
        this.photos = photos;

        if(!photosString.isEmpty())
            photosString.substring(0, photosString.length()-1);
    }

    public String getLengthBefore() {
        return lengthBefore;
    }

    public String getLengthAfter() {
        return lengthAfter;
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDeviceName() {
        return device.getName();
    }
}