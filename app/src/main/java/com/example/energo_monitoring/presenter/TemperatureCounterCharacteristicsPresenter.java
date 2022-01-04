package com.example.energo_monitoring.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.model.DeviceCounter;
import com.example.energo_monitoring.model.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.model.api.ClientInfo;
import com.example.energo_monitoring.model.db.OtherInfo;
import com.example.energo_monitoring.model.db.ResultDataDatabase;
import com.example.energo_monitoring.view.activity.FinalPlacePhotosActivity;
import com.example.energo_monitoring.view.activity.TemperatureCounterCharacteristicsActivity;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesMainListAdapter;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesTabsAdapter;
import com.example.energo_monitoring.view.viewmodel.TemperatureCounterViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TemperatureCounterCharacteristicsPresenter {

    Context context;
    private int currentDevice;
    private List<DeviceCounter> devices;
    private ArrayList<ArrayList<TemperatureCounterCharacteristicsParameter>> parameters;
    TemperatureCounterValuesTabsAdapter adapterTabs;
    TemperatureCounterValuesMainListAdapter adapterParameters;
    TemperatureCounterViewModel model;

    public TemperatureCounterCharacteristicsPresenter(Context context,
                                                      TemperatureCounterViewModel model) {
        this.context = context;
        this.model   = model;

        SharedPreferences mPrefs = context.getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentClientDataBundle", "");
        ClientDataBundle clientDataBundle = gson.fromJson(json, ClientDataBundle.class);

        devices = clientDataBundle.getDeviceCounters();

        parameters = new ArrayList<>();
        for(DeviceCounter deviceCounter : devices) {
            ArrayList<TemperatureCounterCharacteristicsParameter> newParameters = new ArrayList<>();
            newParameters.add(new TemperatureCounterCharacteristicsParameter("Qo Гкал", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("Q гвс", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G1 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G2 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G3 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("T1 C", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("T2 C", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("P1", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("P2", ""));
            parameters.add(newParameters);
        }
    }

    public List<DeviceCounter> getDevices() {
        return devices;
    }

    public ArrayList<TemperatureCounterCharacteristicsParameter> getCurrentParameters() {
        return parameters.get(currentDevice);
    }

    public void setAdapters(TemperatureCounterValuesTabsAdapter adapterTabs,
                            TemperatureCounterValuesMainListAdapter adapterParameters) {
        this.adapterTabs = adapterTabs;
        this.adapterParameters = adapterParameters;
    }

    public TemperatureCounterValuesTabsAdapter getAdapterTabs() {
        return adapterTabs;
    }

    public TemperatureCounterValuesMainListAdapter getAdapterParameters() {
        return adapterParameters;
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
                        otherInfo.counterCharacteristictsComment = comment;
//                        for(int i = 0; i < parameters.size(); ++i){
//                            for(int j = 0; j < parameters.get(i).size(); ++j) {
//                                otherInfo.counterCharacteristicts
//                            }
//                        }

                        db.resultDataDAO().insertOtherInfo(otherInfo);

                        db.resultDataDAO().deleteDeviceCounters(dataId);
                        for(DeviceCounter deviceCounter : devices)
                            deviceCounter.dataId = dataId;
                        db.resultDataDAO().insertDeviceCounters(devices);
                    });

    }
}
