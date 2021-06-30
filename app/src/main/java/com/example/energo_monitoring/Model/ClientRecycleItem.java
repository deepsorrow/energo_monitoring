package com.example.energo_monitoring.Model;

import java.io.Serializable;

public class ClientRecycleItem implements Serializable {
    private String agreementNumber;
    private String knotAddress;
    private String name;
    private String representativeName;
    private String phoneNumber;
    private String email;

    public ClientRecycleItem(String agreementNumber, String knotAddress, String name, String representativeName, String phoneNumber, String email) {
        this.agreementNumber = agreementNumber;
        this.knotAddress = knotAddress;
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

    public String getKnotAddress() {
        return knotAddress;
    }

    public void setKnotAddress(String knotAddress) {
        this.knotAddress = knotAddress;
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
}
