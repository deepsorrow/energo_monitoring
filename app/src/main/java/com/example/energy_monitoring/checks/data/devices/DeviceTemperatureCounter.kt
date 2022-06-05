package com.example.energy_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey
import com.example.energy_monitoring.checks.data.TempParameter

@Entity
class DeviceTemperatureCounter : DeviceInfo() {

    @PrimaryKey(autoGenerate = true)
    override var id = 0

    var unitSystem = Field("")
    var modification = Field("")
    var interval = Field("")
    var parameters = mutableListOf<TempParameter>()

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