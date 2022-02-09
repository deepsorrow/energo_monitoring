package com.example.energo_monitoring.data.api;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class OrganizationInfo implements Serializable {

    @PrimaryKey
    public int id;

    public int dataId;
    public String organizationName;
    public String representativeName;
    public String chiefName;
    public String phoneNumber;
    public String address;
    @Ignore
    public List<String> servingObjects;

    public String servingObjectString;

    public OrganizationInfo() {

    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getServingObjects() {
        return servingObjects;
    }

    public void setServingObjects(List<String> servingObjects) {
        this.servingObjects = servingObjects;
    }
}
