package com.example.energo_monitoring.model.api;

public class ProjectDescription {
    private String photoBase64;
    private String comment;
    private Boolean ok;

    public ProjectDescription() {
    }

    public ProjectDescription(String photoBase64, String comment, Boolean ok) {
        this.photoBase64 = photoBase64;
        this.comment = comment;
        this.ok = ok;
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

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
