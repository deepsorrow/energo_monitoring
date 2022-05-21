package com.example.energo_monitoring.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.energo_monitoring.compose.screens.creatingNew2.CreatingNew2Screen
import com.example.energo_monitoring.compose.screens.creatingNew1.CreatingNew1Screen
import com.example.energo_monitoring.compose.screens.creatingNew3.CreatingNew3Screen
import com.example.energo_monitoring.compose.screens.creatingNew3.CreatingNewDeviceScreen
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.checks.data.api.ServerService
import kotlinx.coroutines.Job

fun NavGraphBuilder.createNewNavGraph(
    navController: NavController,
    clientInfoViewModel: ClientInfoViewModel,
    openDrawer: () -> Job
) {
    navigation(startDestination = "create_new_1", route = "create_new_route") {
        composable(
            route = "create_new_1"
        ) {
            CreatingNew1Screen(
                viewModel = clientInfoViewModel,
                openDrawer = openDrawer,
                navController = navController
            )
        }

        composable(
            route = "create_new_2"
        ) {
            CreatingNew2Screen(
                viewModel = clientInfoViewModel,
                openDrawer = openDrawer,
                navController = navController
            )
        }

        composable(
            route = "create_new_3"
        ) {
            CreatingNew3Screen(
                viewModel = clientInfoViewModel,
                openDrawer = openDrawer,
                navController = navController,
                goToNextScreen = {
                    ServerService.service.sendResults(clientInfoViewModel.assembleData())
                }
            )
        }

        composable(
            route = "create_new_3_device"
        ) {
            CreatingNewDeviceScreen(
                viewModel = clientInfoViewModel,
                openDrawer = openDrawer,
                navController = navController
            )
        }
    }
}
