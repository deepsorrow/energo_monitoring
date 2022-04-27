package com.example.energo_monitoring.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.energo_monitoring.data.api.ClientInfo;
import com.example.energo_monitoring.data.api.OrganizationInfo;
import com.example.energo_monitoring.data.api.ProjectDescription;
import com.example.energo_monitoring.data.db.OtherInfo;
import com.example.energo_monitoring.data.db.ResultDataDAO;
import com.example.energo_monitoring.data.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.data.devices.DeviceCounter;
import com.example.energo_monitoring.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.data.devices.DeviceTemperatureTransducer;

@Database(entities = {ClientInfo.class, ProjectDescription.class, OrganizationInfo.class, DeviceCounter.class,
        DeviceFlowTransducer.class, DevicePressureTransducer.class, DeviceTemperatureCounter.class,
        DeviceTemperatureTransducer.class, OtherInfo.class, FlowTransducerCheckLengthResult.class}, version = 15)
public abstract class ResultDataDatabase extends RoomDatabase {
    public static ResultDataDatabase resultDataDatabase;
    public abstract ResultDataDAO resultDataDAO();

    public static ResultDataDatabase getDatabase(Context context){
        if(resultDataDatabase == null)
            return Room.databaseBuilder(context, ResultDataDatabase.class,
                    "ResultData").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        else
            return resultDataDatabase;
    }
}
