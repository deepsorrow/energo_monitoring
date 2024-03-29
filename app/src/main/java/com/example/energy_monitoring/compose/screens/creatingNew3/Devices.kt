package com.example.energy_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.energy_monitoring.compose.screens.creatingNew1.CreatingTextField
import com.example.energy_monitoring.compose.screens.creatingNew1.IListEntry
import com.example.energy_monitoring.checks.data.api.DeviceInfo
import com.example.energy_monitoring.checks.data.devices.*
import java.math.BigDecimal

interface IDeviceInfo<T : AbstractDevice<*>> : IListEntry {
    val nominative: String
    val genitive: String

    fun factory(): T

    override val listLabel: String
        get() = nominative
}

@Composable
private fun NumberField(placeholder: String, value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        singleLine = true,
        label = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        value = value,
        onValueChange = onValueChanged,
    )
}

@Composable
private fun ComposeCommonFields(device: AbstractDevice<*>) {
    with (device) {
        TextField(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = {
                Text(text = "Модель прибора")
            },
            value = deviceName,
            onValueChange = { deviceName = it },
        )

        NumberField(placeholder = "Номер прибора", deviceNumber?.toString() ?: "") {
            deviceNumber = it.toIntOrNull()
        }
    }
}

sealed class AbstractDevice<D : DeviceInfo> {
    var deviceNumber: Int? by mutableStateOf(null)
    var deviceName by mutableStateOf("")
    var installationPlace: Int? by mutableStateOf(null)

    open val isValid: Boolean
        get() = deviceNumber != null && deviceNumber!! > 0 && installationPlace != null && installationPlace!! > 0

    override fun toString(): String {
        return "$deviceName #$deviceNumber"
    }

    @Composable
    abstract fun compose()

    abstract fun toDataDevice(dataId: Int): D
    abstract fun fromDataDevice(device: D)

    abstract val info: IDeviceInfo<*>
}

class HeatCalculator : AbstractDevice<DeviceTemperatureCounter>() {
    override val isValid: Boolean
        get() = super.isValid && installationPlace != null && installationPlace!! > 0

    @Composable
    override fun compose() {
        ComposeCommonFields(this)
    }

    override fun toDataDevice(dataId: Int): DeviceTemperatureCounter {
        return DeviceTemperatureCounter().also {
            it.dataId = dataId
            it.typeId = 1
            it.deviceNumber.initialValue = deviceNumber?.toString() ?: "0"
            it.deviceName.initialValue = deviceName
        }
    }

    override fun fromDataDevice(device: DeviceTemperatureCounter) {
        deviceNumber = device.deviceNumber.initialValue.toIntOrNull()
        deviceName = device.deviceName.initialValue
    }

    override val info: IDeviceInfo<*>
        get() = Companion

    companion object : IDeviceInfo<HeatCalculator> {
        override val nominative: String
            get() = "Тепловычислитель"
        override val genitive: String
            get() = "тепловычислителя"

        override fun factory() = HeatCalculator()
    }
}

class FlowConverter : AbstractDevice<DeviceFlowTransducer>() {
    var diameter: BigDecimal? by mutableStateOf(null)
    var weight: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            diameter != null &&
            weight != null &&
            diameter!! > BigDecimal.ZERO &&
            weight!! > BigDecimal.ZERO

    override fun toDataDevice(dataId: Int): DeviceFlowTransducer {
        return DeviceFlowTransducer().also {
            it.dataId = dataId
            it.typeId = 2
            it.deviceNumber.initialValue = deviceNumber?.toString() ?: "0"
            it.deviceName.initialValue = deviceName
            it.installationPlace.initialValue = installationPlace?.toString() ?: "0"
            it.diameter.initialValue = diameter?.toString().orEmpty()
            it.impulseWeight.initialValue = weight?.toString().orEmpty()
        }
    }

    override fun fromDataDevice(device: DeviceFlowTransducer) {
        deviceNumber = device.deviceNumber.initialValue.toIntOrNull()
        deviceName = device.deviceName.initialValue
        installationPlace = device.installationPlace.initialValue.toIntOrNull()

        try {
            diameter = BigDecimal(device.diameter.initialValue.trim())
        } catch(_: Throwable) {

        }

        try {
            weight = BigDecimal(device.impulseWeight.initialValue.trim())
        } catch(_: Throwable) {

        }
    }

    @Composable
    override fun compose() {
        ComposeCommonFields(this)

        NumberField(placeholder = "Диаметр", diameter?.toString() ?: "") {
            diameter = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }

        NumberField(placeholder = "Вес импульса", weight?.toString() ?: "") {
            weight = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }
    }

    override val info: IDeviceInfo<*>
        get() = Companion

    companion object : IDeviceInfo<FlowConverter> {
        override val nominative: String
            get() = "Преобразователь расхода"
        override val genitive: String
            get() = "преобразователя расхода"

        override fun factory() = FlowConverter()
    }
}

class TemperatureConverter : AbstractDevice<DeviceTemperatureTransducer>() {
    var length: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            length != null &&
            length!! > BigDecimal.ZERO

    override fun toDataDevice(dataId: Int): DeviceTemperatureTransducer {
        return DeviceTemperatureTransducer().also {
            it.dataId = dataId
            it.typeId = 3
            it.deviceNumber.initialValue = deviceNumber?.toString() ?: "0"
            it.deviceName.initialValue = deviceName
            it.installationPlace.initialValue = installationPlace?.toString() ?: "0"
            it.length.initialValue = length?.toString() ?: "0"
        }
    }

    override fun fromDataDevice(device: DeviceTemperatureTransducer) {
        deviceNumber = device.deviceNumber.initialValue.toIntOrNull()
        deviceName = device.deviceName.initialValue
        installationPlace = device.installationPlace.initialValue.toIntOrNull()

        try {
            length = BigDecimal(device.length.initialValue.trim())
        } catch(_: Throwable) {

        }
    }

    @Composable
    override fun compose() {
        ComposeCommonFields(this)

        NumberField(placeholder = "Диаметр", length?.toString() ?: "") {
            length = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }
    }

    override val info: IDeviceInfo<*>
        get() = Companion

    companion object : IDeviceInfo<TemperatureConverter> {
        override val nominative: String
            get() = "Преобразователь температуры"
        override val genitive: String
            get() = "преобразователя температуры"

        override fun factory() = TemperatureConverter()
    }
}

class PressureConverter : AbstractDevice<DevicePressureTransducer>() {
    var min: BigDecimal? by mutableStateOf(null)
    var max: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            min != null &&
            max != null &&
            min!! > BigDecimal.ZERO &&
            max!! >= min!!

    override fun toDataDevice(dataId: Int): DevicePressureTransducer {
        return DevicePressureTransducer().also {
            it.dataId = dataId
            it.typeId = 4
            it.deviceNumber.initialValue = deviceNumber?.toString() ?: "0"
            it.deviceName.initialValue = deviceName
            it.installationPlace.initialValue = installationPlace?.toString() ?: "0"
            it.sensorRange.initialValue = "${min?.toString() ?: "0"}-${max?.toString() ?: "0"}"
        }
    }

    override fun fromDataDevice(device: DevicePressureTransducer) {
        deviceNumber = device.deviceNumber.initialValue.toIntOrNull()
        deviceName = device.deviceName.initialValue
        installationPlace = device.installationPlace.initialValue.toIntOrNull()

        try {
            val split = device.sensorRange.initialValue.split('-')

            if (split.size != 2)
                return

            min = BigDecimal(split[0].trim())
            max = BigDecimal(split[1].trim())
        } catch(_: Throwable) {

        }
    }

    @Composable
    override fun compose() {
        ComposeCommonFields(this)

        NumberField(placeholder = "Минимальное давление", min?.toString() ?: "") {
            min = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }

        NumberField(placeholder = "Максимальное давление", max?.toString() ?: "") {
            max = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }
    }

    override val info: IDeviceInfo<*>
        get() = Companion

    companion object : IDeviceInfo<PressureConverter> {
        override val nominative: String
            get() = "Преобразователь давления"
        override val genitive: String
            get() = "преобразователя давления"

        override fun factory() = PressureConverter()
    }
}

class Counter : AbstractDevice<DeviceCounter>() {
    var diameter: BigDecimal? by mutableStateOf(null)
    var weight: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            diameter != null &&
            weight != null &&
            diameter!! > BigDecimal.ZERO &&
            weight!! > BigDecimal.ZERO

    override fun toDataDevice(dataId: Int): DeviceCounter {
        return DeviceCounter().also {
            it.dataId = dataId
            it.typeId = 5
            it.deviceNumber.initialValue = deviceNumber?.toString() ?: "0"
            it.deviceName.initialValue = deviceName
            it.diameter.initialValue = diameter?.toString() ?: "0"
            it.impulseWeight.initialValue = weight?.toString() ?: "0"
        }
    }

    override fun fromDataDevice(device: DeviceCounter) {
        deviceNumber = device.deviceNumber.initialValue.toIntOrNull()
        deviceName = device.deviceName.initialValue
        // numberOfDevice = device.installationPlace.toIntOrNull()

        try {
            diameter = BigDecimal(device.diameter.initialValue.trim())
        } catch(_: Throwable) {

        }

        try {
            weight = BigDecimal(device.impulseWeight.initialValue.trim())
        } catch(_: Throwable) {

        }
    }

    @Composable
    override fun compose() {
        ComposeCommonFields(this)

        NumberField(placeholder = "Диаметр", diameter?.toString() ?: "") {
            diameter = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }

        NumberField(placeholder = "Вес импульса", weight?.toString() ?: "") {
            weight = try {
                BigDecimal(it)
            } catch(err: Throwable) {
                null
            }
        }
    }

    override val info: IDeviceInfo<*>
        get() = Companion

    companion object : IDeviceInfo<Counter> {
        override val nominative: String
            get() = "Механический счетчик"
        override val genitive: String
            get() = "механического счетчика"

        override fun factory() = Counter()
    }
}
