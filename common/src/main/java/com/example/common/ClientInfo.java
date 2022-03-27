package com.example.common;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ClientInfo implements Serializable {

    @PrimaryKey
    public int id;

    public int dataId;
    private String agreementNumber;
    private String addressUUTE;
    private String name;
    private String representativeName;
    private String phoneNumber;
    private String email;
    private Boolean hasDebt;

    public ClientInfo() {
    }

    public ClientInfo(String agreementNumber, String addressUUTE, String name, String representativeName, String phoneNumber, String email) {
        this.agreementNumber = agreementNumber;
        this.addressUUTE = addressUUTE;
        this.name = name;
        this.representativeName = representativeName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getAddressUUTE() {
        return addressUUTE;
    }

    public void setAddressUUTE(String addressUUTE) {
        this.addressUUTE = addressUUTE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getdataId() {
        return dataId;
    }

    public Boolean getHasDebt() {
        return hasDebt;
    }

    public void setHasDebt(Boolean hasDebt) {
        this.hasDebt = hasDebt;
    }
}
