package com.example.common.api;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ProjectDescription {
    @PrimaryKey
    public int id;
    public int dataId;

    @Ignore
    private String photoBase64;
    public String photoPath;
    private String comment;
    public boolean isOk;

    public ProjectDescription() {
    }

    public ProjectDescription(String photoBase64, String comment, boolean isOk) {
        this.photoBase64 = photoBase64;
        this.comment = comment;
        this.isOk = isOk;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
