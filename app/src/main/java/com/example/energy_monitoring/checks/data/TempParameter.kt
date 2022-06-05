package com.example.energy_monitoring.checks.data

data class TempParameter(var name: String, var unitSystem: String, var value: String) {

    constructor(name: String, unitSystem: String) : this(name, unitSystem, "")
}