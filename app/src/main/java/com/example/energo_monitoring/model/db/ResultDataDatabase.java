package com.example.energo_monitoring.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.energo_monitoring.model.DeviceCounter;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;
import com.example.energo_monitoring.model.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.model.api.ClientInfo;
import com.example.energo_monitoring.model.api.OrganizationInfo;
import com.example.energo_monitoring.model.api.ProjectDescription;

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
