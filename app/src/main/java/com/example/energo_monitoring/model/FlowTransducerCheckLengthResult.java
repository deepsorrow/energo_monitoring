package com.example.energo_monitoring.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.energo_monitoring.R;

import java.util.ArrayList;

public class FlowTransducerCheckLengthResult {
    private String lengthBefore = "";
    private String lengthAfter = "";
    private ArrayList<Bitmap> photos;
    private DeviceFlowTransducer device;
    private int icon;

    public FlowTransducerCheckLengthResult(DeviceFlowTransducer device) {
        this.device = device;
        this.icon = R.drawable.ic_baseline_trip_origin_24;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public void setLengthAfter(String lengthAfter) {
        this.lengthAfter = lengthAfter;
    }

    public void setPhotos(ArrayList<Bitmap> photos) {
        this.photos = photos;
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
