package com.example.energo_monitoring.checks.ui.presenters;

import android.content.Context;

import com.example.energo_monitoring.checks.data.devices.DeviceCounter;
import com.example.energo_monitoring.checks.data.TempParameter;
import com.example.energo_monitoring.checks.data.db.OtherInfo;
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase;
import com.example.energo_monitoring.checks.ui.viewmodel.TemperatureCounterViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TemperatureCounterCharacteristicsPresenter {

    private final Context context;
    private int currentDevice;
    private final List<DeviceCounter> devices;
    public ArrayList<ArrayList<TempParameter>> parameters;
    private final TemperatureCounterViewModel model;

    public TemperatureCounterCharacteristicsPresenter(Context context, int dataId,
                                                      TemperatureCounterViewModel model) {
        this.context = context;
        this.model   = model;

        devices = ResultDataDatabase.getDatabase(context).resultDataDAO().getDeviceCounters(dataId);

        if (devices != null) {
            parameters = new ArrayList<>();
            for (DeviceCounter deviceCounter : devices) {
                ArrayList<TempParameter> newParameters = new ArrayList<>();
                newParameters.add(new TempParameter("Qo Гкал", ""));
                newParameters.add(new TempParameter("Q гвс", ""));
                newParameters.add(new TempParameter("G1 т/час", ""));
                newParameters.add(new TempParameter("G2 т/час", ""));
                newParameters.add(new TempParameter("G3 т/час", ""));
                newParameters.add(new TempParameter("T1 C", ""));
                newParameters.add(new TempParameter("T2 C", ""));
                newParameters.add(new TempParameter("P1", ""));
                newParameters.add(new TempParameter("P2", ""));
                parameters.add(newParameters);
            }
        }
    }

    public List<DeviceCounter> getDevices() {
        return devices;
    }

    public ArrayList<TempParameter> getCurrentParameters() {
        return parameters.get(currentDevice);
    }

    public int getCurrentDevice() {
        return currentDevice;
    }

    public void setCurrentDevice(int currentDevice) {
        this.currentDevice = currentDevice;
    }

    public TemperatureCounterViewModel getModel(){
        return model;
    }

    public void insertDataToDb(int dataId, String comment){

            ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
            Observable.just(db)
                    .subscribeOn(Schedulers.io())
                    .subscribe((value) -> {
                        OtherInfo otherInfo = db.resultDataDAO().getOtherInfo(dataId);

                        Gson gson = new Gson();
                        otherInfo.counterCharacteristicts = gson.toJson(parameters);
                        otherInfo.localVersion += 1;
                        otherInfo.counterCharacteristictsComment = comment;

                        db.resultDataDAO().insertOtherInfo(otherInfo);

                        db.resultDataDAO().deleteDeviceCounters(dataId);
                        for(DeviceCounter deviceCounter : devices)
                            deviceCounter.dataId = dataId;
                        db.resultDataDAO().insertDeviceCounters(devices);
                    });

    }

    public ArrayList<ArrayList<TempParameter>> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ArrayList<TempParameter>> parameters) {
        this.parameters = parameters;
    }
}
