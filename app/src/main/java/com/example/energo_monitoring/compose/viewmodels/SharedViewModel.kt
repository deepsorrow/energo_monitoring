package com.example.energo_monitoring.compose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.example.energo_monitoring.compose.DrawerScreens

class SharedViewModel : ViewModel() {
    var destination by mutableStateOf(DrawerScreens.Checks.route)
        private set

    val changeDestination: (String, NavOptionsBuilder.() -> Unit) -> Unit = { route: String, doBefore: NavOptionsBuilder.() -> Unit ->
        navOptions(doBefore)
        destination = route
    }
}