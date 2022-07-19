package com.example.energy_monitoring.compose

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.energy_monitoring.compose.navigation.createNewNavGraph
import com.example.energy_monitoring.compose.navigation.drawerNavGraph
import com.example.energy_monitoring.compose.screens.drawer.Drawer
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energy_monitoring.compose.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun SetupNavigation(clientInfoViewModel: ClientInfoViewModel = hiltViewModel(),
                    sharedViewModel: SharedViewModel){


    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = sharedViewModel.currentRoute) {
        if (navController.currentDestination?.route != null
            && sharedViewModel.currentRoute != navController.currentDestination?.route) {
            navController.navigate(route = sharedViewModel.currentRoute)
        }
    }

    val openDrawer = { scope.launch { drawerState.open() } }
    val closeDrawer = { scope.launch { drawerState.close() } }
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(onDestinationClicked = { route ->
                sharedViewModel.changeDestination(route) {
                    launchSingleTop = true
                    closeDrawer()
                }
            })
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = DrawerScreens.Checks.route,
            route = "drawer_route"
        ) {
            drawerNavGraph(
                openDrawer = openDrawer,
                navController = navController
            )
            createNewNavGraph(
                navController = navController,
                clientInfoViewModel = clientInfoViewModel,
                openDrawer = openDrawer
            )
        }
    }
}

@Composable
@Preview
private fun MainMenuPreview(){
//    SetupNavigation(
//        ClientInfoViewModel(),
//        SharedViewModel()
//    )
}