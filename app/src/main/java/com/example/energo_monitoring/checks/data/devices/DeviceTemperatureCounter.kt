package com.example.energo_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey

@Entity
class DeviceTemperatureCounter : DeviceInfo() {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    var unitSystem = Field("")
    var modification = Field("")
    var interval = Field("")

    var comment = Field("")

    //    public int getFilledState() {
    //        if(!(unitSystem == null || unitSystem.isEmpty())
    //                && !(modification == null || modification.isEmpty())
    //                && !(interval == null || interval.isEmpty()))
    //            return 2;
    //        else if(!(unitSystem == null || unitSystem.isEmpty())
    //                || !(modification == null || modification.isEmpty())
    //                || !(interval == null || interval.isEmpty()))
    //            return 1;
    //        else
    //            return 0;
    //    }
}