package com.example.energo_monitoring.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class DeviceFlowTransducerCheckInfo {
    private String lengthBefore;
    private String lengthAfter;
    private ArrayList<Bitmap> photos;

    public String getLengthBefore() {
        return lengthBefore;
    }

    public void setLengthBefore(String lengthBefore) {
        this.lengthBefore = lengthBefore;
    }

    public String getLengthAfter() {
        return lengthAfter;
    }

    public void setLengthAfter(String lengthAfter) {
        this.lengthAfter = lengthAfter;
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Bitmap> photos) {
        this.photos = photos;
    }

    public void addPhoto(Bitmap bitmap){
        photos.add(bitmap);
    }
}
