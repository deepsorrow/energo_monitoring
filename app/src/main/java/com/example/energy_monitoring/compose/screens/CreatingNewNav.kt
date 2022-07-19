package com.example.energy_monitoring.compose.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.DrawerScreens

private sealed class CreatingNewNav(val label: String, val path: String, val drawable: Int) {
    object Info : CreatingNewNav("Общее", "create_new_1", R.drawable.ic_baseline_assignment_24)
    object Photos : CreatingNewNav("Файлы", "create_new_2", R.drawable.ic_baseline_attach_file_24)
    object Devices : CreatingNewNav("Устройства", "create_new_3", R.drawable.ic_baseline_device_hub_24)

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
        val currentRoute = navStack?.destination?.route

        for (dest in CreatingNewNav.listing) {
            BottomNavigationItem(
                selected = currentRoute == dest.path,
                onClick = {
                    if (currentRoute != dest.path) {
                        navController.navigate(dest.path) {
                            launchSingleTop = true
                            popUpTo(DrawerScreens.Checks.route) {
                                inclusive = false
                            }
                        }
                    }
                },
                label = {
                    Text(
                        text = dest.label
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = dest.drawable),
                        contentDescription = dest.label
                    )
                },
            )
        }
    }
}
