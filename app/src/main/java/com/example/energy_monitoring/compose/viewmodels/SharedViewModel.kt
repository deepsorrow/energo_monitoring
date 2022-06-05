package com.example.energy_monitoring.compose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.example.energy_monitoring.compose.DrawerScreens

class SharedViewModel : ViewModel() {
    var currentRoute by mutableStateOf(DrawerScreens.Checks.route)
        private set

    val changeDestination: (String, NavOptionsBuilder.() -> Unit) -> Unit = { route: String, doBefore: NavOptionsBuilder.() -> Unit ->
        navOptions(doBefore)
        currentRoute = "" // быстрофикс чтобы сменить state, почему-то не переключается иногда
        currentRoute = route
    }
}