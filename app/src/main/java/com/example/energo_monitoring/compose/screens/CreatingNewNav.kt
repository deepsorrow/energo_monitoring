package com.example.energo_monitoring.compose.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

private sealed class CreatingNewNav(val label: String, val path: String) {
    object Info : CreatingNewNav("Инфо", "create_new_1")
    object Photos : CreatingNewNav("Схемы", "create_new_2")
    object Devices : CreatingNewNav("Устройства", "create_new_3")

    companion object {
        val listing = listOf(Info, Photos, Devices)
    }
}

@Composable
fun CreatingNewNavBottom(
    navController: NavController
) {
    BottomNavigation {
        val navStack by navController.currentBackStackEntryAsState()
        val route = navStack?.destination?.route

        for (dest in CreatingNewNav.listing) {
            BottomNavigationItem(
                selected = route == dest.path,
                onClick = {
                    navController.navigate(dest.path)
                },
                label = {
                    Text(
                        text = dest.label
                    )
                },
                icon = {},
            )
        }
    }
}
