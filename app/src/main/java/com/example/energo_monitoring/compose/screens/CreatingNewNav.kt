package com.example.energo_monitoring.compose.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.energo_monitoring.R

private sealed class CreatingNewNav(val label: String, val path: String, val drawable: Int) {
    object Info : CreatingNewNav("Инфо", "create_new_1", R.drawable.ic_baseline_assignment_24)
    object Photos : CreatingNewNav("Схемы", "create_new_2", R.drawable.ic_baseline_attach_file_24)
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
                icon = {
                    Icon(painter = painterResource(id = dest.drawable), contentDescription = dest.label)
                },
            )
        }
    }
}
