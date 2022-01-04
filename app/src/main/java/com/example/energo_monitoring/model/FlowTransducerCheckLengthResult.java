package com.example.energo_monitoring.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.energo_monitoring.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@Entity
public class FlowTransducerCheckLengthResult {
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

    public FlowTransducerCheckLengthResult() {
    }

    public FlowTransducerCheckLengthResult(int id, DeviceFlowTransducer device, int dataId) {
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

//        photosBase64 = "";
//        // i = 1 because first photo is image button
//        for(int i = 0; i < photos.size() - 1; ++i) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            photos.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            byte[] byteArray = byteArrayOutputStream.toByteArray();
//            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//            photosBase64 += encoded + ";";
//        }
//        // delete ';' from the end
//        if(!photosBase64.isEmpty())
//            photosBase64.substring(0, photosBase64.length()-1);
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