package com.example.energo_monitoring.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.compose.screens.referenceInfo.DocumentsScreen
import com.example.energo_monitoring.compose.screens.mainMenu.checks.ChecksScreen
import kotlinx.coroutines.Job

fun NavGraphBuilder.drawerNavGraph(
    openDrawer: () -> Job,
    navController: NavController
){
    val openCreateNewScreen = { navController.navigate(route = "create_new_route") }
    composable(DrawerScreens.Checks.route) {
        ChecksScreen(openDrawer = openDrawer, openCreateNewScreen)
    }

    composable(DrawerScreens.ReferenceInfo.route) {
        DocumentsScreen(openDrawer = openDrawer)
    }
}