package com.example.energo_monitoring.checks.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.energo_monitoring.checks.data.ProjectFile;
import com.example.energo_monitoring.checks.data.api.ClientInfo;
import com.example.energo_monitoring.checks.data.api.OrganizationInfo;
import com.example.energo_monitoring.checks.data.api.ProjectDescription;
import com.example.energo_monitoring.checks.data.FlowTransducerLength;
import com.example.energo_monitoring.checks.data.devices.DeviceCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer;

@TypeConverters({FieldConverter.class})
@Database(entities = {ClientInfo.class, ProjectDescription.class, OrganizationInfo.class, DeviceCounter.class,
        DeviceFlowTransducer.class, DevicePressureTransducer.class, DeviceTemperatureCounter.class,
        DeviceTemperatureTransducer.class, OtherInfo.class, FlowTransducerLength.class, ProjectFile.class}, version = 24)
public abstract class ResultDataDatabase extends RoomDatabase {
    private static ResultDataDatabase resultDataDatabase;
    public abstract ResultDataDAO resultDataDAO();

    public static ResultDataDatabase getDatabase(Context context){
        if (resultDataDatabase == null)
            resultDataDatabase = Room.databaseBuilder(context, ResultDataDatabase.class,
                    "ResultData").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        return resultDataDatabase;
    }
}
