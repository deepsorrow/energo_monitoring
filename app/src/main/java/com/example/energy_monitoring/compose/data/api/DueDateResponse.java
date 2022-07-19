package com.example.energy_monitoring.compose.data.api;

import java.util.HashMap;

public class DueDateResponse {
    private Boolean success;
    private String result;
    private HashMap<String, String> variants;
    public int variantsCount;

    public DueDateResponse() {

    }

    public DueDateResponse(Boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public DueDateResponse(HashMap<String, String> variants) {
        this.success = true;
        this.variants = variants;
        this.variantsCount = variants.size();
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HashMap<String, String> getVariants() {
        return variants;
    }

    public void setVariants(HashMap<String, String> variants) {
        this.variants = variants;
    }
}
