package com.example.energy_monitoring.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.energy_monitoring.compose.screens.creatingNew2.CreatingNew2Screen
import com.example.energy_monitoring.compose.screens.creatingNew1.CreatingNew1Screen
import com.example.energy_monitoring.compose.screens.creatingNew3.CreatingNew3Screen
import com.example.energy_monitoring.compose.screens.creatingNew3.CreatingNewDeviceScreen
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energy_monitoring.checks.data.api.ServerService
import com.example.energy_monitoring.compose.DrawerScreens
import kotlinx.coroutines.Job

fun NavGraphBuilder.createNewNavGraph(
    clientInfoViewModel: ClientInfoViewModel,
    navController: NavController,
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
                sendResults = { context ->
                    //ServerService.service.sendResults(clientInfoViewModel.assembleData())
                    clientInfoViewModel.saveDataToRepository(context = context)
                    navController.navigate(DrawerScreens.Checks.route) {
                        popUpTo(DrawerScreens.Checks.route) {
                            inclusive = false
                        }
                    }
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
