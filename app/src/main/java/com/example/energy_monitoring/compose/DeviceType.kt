package com.example.energy_monitoring.compose

enum class DeviceType(val title: String, val typeId: Int) {
    TemperatureCounter(title = "Тепловычислитель", 1),
    FlowTransducer(title = "Преобразователь расхода", 2),
    TemperatureTransducer(title = "Преобразователь температуры", 3),
    PressureTransducer(title = "Преобразователь давления", 4),
    Any(title = "", 0)
}