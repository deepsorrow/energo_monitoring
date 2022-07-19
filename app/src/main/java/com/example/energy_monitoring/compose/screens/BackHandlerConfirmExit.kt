package com.example.energy_monitoring.compose.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.energy_monitoring.compose.DrawerScreens

@Composable
fun BackHandlerConfirmExit(navController: NavController, showAlertDialog: Boolean, toShowAlertDialog: () -> Unit) {
    BackHandler {
        // Если предупреждение уже открыто, то можно выходить
        if (showAlertDialog) {
            navController.popBackStack(DrawerScreens.Checks.route, false)
        } else {
            toShowAlertDialog()
        }
    }
}