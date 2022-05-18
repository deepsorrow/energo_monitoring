package com.example.energo_monitoring.checks.ui.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InspectionDeviceViewModel extends ViewModel {
    private MutableLiveData<Integer> currentDeviceId;

    public MutableLiveData<Integer> getCurrentDeviceId() {
        if(currentDeviceId == null){
            currentDeviceId = new MutableLiveData<>();
        }

        return currentDeviceId;
    }

    public void setCurrentDeviceId(MutableLiveData<Integer> currentDeviceId) {
        this.currentDeviceId = currentDeviceId;
    }
}