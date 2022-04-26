package com.example.energo_monitoring.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.compose.screens.mainMenu.ChecksScreen
import com.example.energo_monitoring.compose.screens.syncScreen.SyncScreen
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.compose.viewmodels.SyncViewModel
import kotlinx.coroutines.Job

fun NavGraphBuilder.drawerNavGraph(
    checksViewModel: ChecksViewModel,
    syncViewModel: SyncViewModel,
    openDrawer: () -> Job,
    navController: NavController
){
    val openCreateNewScreen = { navController.navigate(route = "create_new_route") }
    composable(DrawerScreens.Checks.route) {
        ChecksScreen(viewModel = checksViewModel, openDrawer = openDrawer, openCreateNewScreen)
    }

    composable(DrawerScreens.Sync.route) {
        SyncScreen(viewModel = syncViewModel, openDrawer = openDrawer)
    }
}