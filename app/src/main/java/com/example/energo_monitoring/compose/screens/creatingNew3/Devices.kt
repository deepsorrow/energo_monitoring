package com.example.energo_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingTextField
import com.example.energo_monitoring.compose.screens.creatingNew1.IListEntry
import java.math.BigDecimal

interface IDeviceInfo<T : AbstractDevice> : IListEntry {
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

sealed class AbstractDevice {
    var certificateNum: Int? by mutableStateOf(null)
    var model by mutableStateOf("")

    open val isValid: Boolean
        get() = certificateNum != null && certificateNum!! > 0

    @Composable
    open fun compose() {
        CreatingTextField(placeholder = "Номер свидетельства", certificateNum?.toString() ?: "") {
            certificateNum = it.toIntOrNull()
        }

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Проверить")
        }

        CreatingTextField(placeholder = "Модель прибора", model) {
            model = it
        }
    }
}

class HeatCalculator : AbstractDevice() {
    var numberOfDevice: Int? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid && numberOfDevice != null && numberOfDevice!! > 0

    override fun toString(): String {
        return "$nominative: $model, $numberOfDevice"
    }

    @Composable
    override fun compose() {
        super.compose()

        NumberField(placeholder = "Номер прибора", numberOfDevice?.toString() ?: "") {
            numberOfDevice = it.toIntOrNull()
        }
    }

    companion object : IDeviceInfo<HeatCalculator> {
        override val nominative: String
            get() = "Тепловычислитель"
        override val genitive: String
            get() = "Тепловычислителя"

        override fun factory() = HeatCalculator()
    }
}

class FlowConverter : AbstractDevice() {
    var numberOfDevice: Int? by mutableStateOf(null)
    var diameter: BigDecimal? by mutableStateOf(null)
    var weight: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            numberOfDevice != null &&
            diameter != null &&
            weight != null &&
            numberOfDevice!! > 0 &&
            diameter!! > BigDecimal.ZERO &&
            weight!! > BigDecimal.ZERO

    override fun toString(): String {
        return "$nominative: $model, $numberOfDevice, $diameter, $weight"
    }

    @Composable
    override fun compose() {
        super.compose()

        NumberField(placeholder = "Номер прибора", numberOfDevice?.toString() ?: "") {
            numberOfDevice = it.toIntOrNull()
        }

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
            get() = "Преобразователя расхода"

        override fun factory() = FlowConverter()
    }
}

class TemperatureConverter : AbstractDevice() {
    var numberOfSensor: Int? by mutableStateOf(null)
    var length: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            numberOfSensor != null &&
            length != null &&
            numberOfSensor!! > 0 &&
            length!! > BigDecimal.ZERO

    override fun toString(): String {
        return "$nominative: $model, $numberOfSensor, $length"
    }

    @Composable
    override fun compose() {
        super.compose()

        NumberField(placeholder = "Номер датчика", numberOfSensor?.toString() ?: "") {
            numberOfSensor = it.toIntOrNull()
        }

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
            get() = "Преобразователя температуры"

        override fun factory() = TemperatureConverter()
    }
}

class PressureConverter : AbstractDevice() {
    var numberOfDevice: Int? by mutableStateOf(null)
    var min: BigDecimal? by mutableStateOf(null)
    var max: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            numberOfDevice != null &&
            min != null &&
            max != null &&
            numberOfDevice!! > 0 &&
            min!! > BigDecimal.ZERO &&
            max!! >= min!!

    override fun toString(): String {
        return "$nominative: $model, $min-$max"
    }

    @Composable
    override fun compose() {
        super.compose()

        NumberField(placeholder = "Номер прибора", numberOfDevice?.toString() ?: "") {
            numberOfDevice = it.toIntOrNull()
        }

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
            get() = "Преобразователя давления"

        override fun factory() = PressureConverter()
    }
}

class Counter : AbstractDevice() {
    var numberOfDevice: Int? by mutableStateOf(null)
    var diameter: BigDecimal? by mutableStateOf(null)
    var weight: BigDecimal? by mutableStateOf(null)

    override val isValid: Boolean
        get() = super.isValid &&
            numberOfDevice != null &&
            diameter != null &&
            weight != null &&
            numberOfDevice!! > 0 &&
            diameter!! > BigDecimal.ZERO &&
            weight!! > BigDecimal.ZERO

    override fun toString(): String {
        return "$nominative: $model, $numberOfDevice, $diameter, $weight"
    }

    @Composable
    override fun compose() {
        super.compose()

        NumberField(placeholder = "Номер прибора", numberOfDevice?.toString() ?: "") {
            numberOfDevice = it.toIntOrNull()
        }

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
            get() = "Счётчика"

        override fun factory() = Counter()
    }
}
