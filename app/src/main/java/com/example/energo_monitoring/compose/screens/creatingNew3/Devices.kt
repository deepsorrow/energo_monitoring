package com.example.energo_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingTextField
import com.example.energo_monitoring.compose.screens.creatingNew1.IListEntry
import com.example.energo_monitoring.data.api.DeviceInfo
import com.example.energo_monitoring.data.devices.DeviceCounter
import com.example.energo_monitoring.data.devices.DeviceFlowTransducer
import com.example.energo_monitoring.data.devices.DevicePressureTransducer
import com.example.energo_monitoring.data.devices.DeviceTemperatureTransducer
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
    OutlinedTextField(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
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
        CreatingTextField(placeholder = "Номер свидетельства", deviceNumber?.toString() ?: "") {
            deviceNumber = it.toIntOrNull()
        }

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Проверить")
        }

        CreatingTextField(placeholder = "Модель прибора", deviceName) {
            deviceName = it
        }

        NumberField(placeholder = "Номер прибора", installationPlace?.toString() ?: "") {
            installationPlace = it.toIntOrNull()
        }
    }
}

sealed class AbstractDevice<D : DeviceInfo> {
    var deviceNumber: Int? by mutableStateOf(null)
    var deviceName by mutableStateOf("")
    var installationPlace: Int? by mutableStateOf(null)

    open val isValid: Boolean
        get() = deviceNumber != null && deviceNumber!! > 0 && installationPlace != null && installationPlace!! > 0

    @Composable
    abstract fun compose()

    abstract fun toDataDevice(): D
    abstract fun fromDataDevice(device: D)
}

class HeatCalculator : AbstractDevice<DeviceTemperatureTransducer>() {
    override val isValid: Boolean
        get() = super.isValid && installationPlace != null && installationPlace!! > 0

    override fun toString(): String {
        return "$nominative: $deviceName, $installationPlace"
    }

    @Composable
    override fun compose() {
        ComposeCommonFields(this)
    }

    override fun toDataDevice(): DeviceTemperatureTransducer {
        return DeviceTemperatureTransducer().also {
            it.deviceNumber = deviceNumber?.toString() ?: "0"
            it.deviceName = deviceName
            it.installationPlace = installationPlace?.toString() ?: "0"
        }
    }

    override fun fromDataDevice(device: DeviceTemperatureTransducer) {
        deviceNumber = device.deviceNumber.toIntOrNull()
        deviceName = device.deviceName
        installationPlace = device.installationPlace.toIntOrNull()
    }

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

    override fun toString(): String {
        return "$nominative: $deviceName, $installationPlace, $diameter, $weight"
    }

    override fun toDataDevice(): DeviceFlowTransducer {
        return DeviceFlowTransducer().also {
            it.deviceNumber = deviceNumber?.toString() ?: "0"
            it.deviceName = deviceName
            it.installationPlace = installationPlace?.toString() ?: "0"
            it.diameter = diameter?.toString()
            it.impulseWeight = weight?.toString()
        }
    }

    override fun fromDataDevice(device: DeviceFlowTransducer) {
        deviceNumber = device.deviceNumber.toIntOrNull()
        deviceName = device.deviceName
        installationPlace = device.installationPlace.toIntOrNull()

        try {
            diameter = BigDecimal(device.diameter.trim())
        } catch(_: Throwable) {

        }

        try {
            weight = BigDecimal(device.impulseWeight.trim())
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

    override fun toString(): String {
        return "$nominative: $deviceName, $installationPlace, $length"
    }

    override fun toDataDevice(): DeviceTemperatureTransducer {
        return DeviceTemperatureTransducer().also {
            it.deviceNumber = deviceNumber?.toString() ?: "0"
            it.deviceName = deviceName
            it.installationPlace = installationPlace?.toString() ?: "0"
            it.length = length?.toString() ?: "0"
        }
    }

    override fun fromDataDevice(device: DeviceTemperatureTransducer) {
        deviceNumber = device.deviceNumber.toIntOrNull()
        deviceName = device.deviceName
        installationPlace = device.installationPlace.toIntOrNull()

        try {
            length = BigDecimal(device.length.trim())
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

    override fun toString(): String {
        return "$nominative: $deviceName, $min-$max"
    }

    override fun toDataDevice(): DevicePressureTransducer {
        return DevicePressureTransducer().also {
            it.deviceNumber = deviceNumber?.toString() ?: "0"
            it.deviceName = deviceName
            it.installationPlace = installationPlace?.toString() ?: "0"
            it.sensorRange = "${min?.toString() ?: "0"}-${max?.toString() ?: "0"}"
        }
    }

    override fun fromDataDevice(device: DevicePressureTransducer) {
        deviceNumber = device.deviceNumber.toIntOrNull()
        deviceName = device.deviceName
        installationPlace = device.installationPlace.toIntOrNull()

        try {
            val split = device.sensorRange.split('-')

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

    override fun toString(): String {
        return "$nominative: $deviceName, $installationPlace, $diameter, $weight"
    }

    override fun toDataDevice(): DeviceCounter {
        return DeviceCounter().also {
            it.deviceNumber = deviceNumber?.toString() ?: "0"
            it.deviceName = deviceName
            it.diameter = diameter?.toString() ?: "0"
            it.impulseWeight = weight?.toString() ?: "0"
        }
    }

    override fun fromDataDevice(device: DeviceCounter) {
        deviceNumber = device.deviceNumber.toIntOrNull()
        deviceName = device.deviceName
        // numberOfDevice = device.installationPlace.toIntOrNull()

        try {
            diameter = BigDecimal(device.diameter.trim())
        } catch(_: Throwable) {

        }

        try {
            weight = BigDecimal(device.impulseWeight.trim())
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

    companion object : IDeviceInfo<Counter> {
        override val nominative: String
            get() = "Счётчик"
        override val genitive: String
            get() = "счётчика"

        override fun factory() = Counter()
    }
}
