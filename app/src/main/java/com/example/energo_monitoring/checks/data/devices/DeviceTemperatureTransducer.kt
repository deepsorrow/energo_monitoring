package com.example.energo_monitoring.checks.data.devices

import androidx.room.Entity
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import androidx.room.PrimaryKey

@Entity
class DeviceTemperatureTransducer : DeviceInfo() {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @JvmField
    var dataId = 0
    var installationPlace = Field("")
    var values = Field("")
    var length = Field("")
    var comment = Field("")

    //    public int getFilledState() {
    //        if(!(installationPlace == null || installationPlace.isEmpty())
    //                && !(length == null || length.isEmpty())
    //                && !(values == null || values.isEmpty()))
    //            return 2;
    //        else if(!(installationPlace == null || installationPlace.isEmpty())
    //                || !(length == null || length.isEmpty())
    //                || !(values == null || values.isEmpty()))
    //            return 1;
    //        else
    //            return 0;
    //    }
}