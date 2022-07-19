package com.example.energy_monitoring.checks.data.db

import android.content.Context
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.api.ProjectDescription
import com.example.energy_monitoring.checks.data.api.OrganizationInfo
import com.example.energy_monitoring.checks.data.devices.DeviceCounter
import com.example.energy_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energy_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energy_monitoring.checks.data.CheckLengthResult
import com.example.energy_monitoring.checks.data.files.ProjectFile
import com.example.energy_monitoring.checks.data.files.CheckLengthPhotoFile
import com.example.energy_monitoring.checks.data.files.FinalPhotoFile
import androidx.room.RoomDatabase
import androidx.room.Room

@TypeConverters(FieldConverter::class, TempParameterConverter::class, FinalPhotoConverter::class)
@Database(
    entities = [ClientInfo::class, ProjectDescription::class, OrganizationInfo::class,
        DeviceCounter::class, DeviceFlowTransducer::class, DevicePressureTransducer::class,
        DeviceTemperatureCounter::class, DeviceTemperatureTransducer::class, OtherInfo::class,
        CheckLengthResult::class, ProjectFile::class, CheckLengthPhotoFile::class, FinalPhotoFile::class],
    version = 33
)
abstract class ResultDataDatabase : RoomDatabase() {
    abstract fun resultDataDAO(): ResultDataDAO

    companion object {
        private var resultDataDatabase: ResultDataDatabase? = null
        fun getDatabase(context: Context?): ResultDataDatabase {
            if (resultDataDatabase == null) {
                resultDataDatabase = Room
                    .databaseBuilder(context!!, ResultDataDatabase::class.java, "ResultData")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return resultDataDatabase!!
        }
    }
}