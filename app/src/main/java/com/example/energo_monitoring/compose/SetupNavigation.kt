package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.energo_monitoring.compose.DrawerScreens
import com.example.energo_monitoring.compose.navigation.createNewNavGraph
import com.example.energo_monitoring.compose.navigation.drawerNavGraph
import com.example.energo_monitoring.compose.screens.drawer.Drawer
import com.example.energo_monitoring.compose.screens.mainMenu.ChecksScreen
import com.example.energo_monitoring.compose.screens.syncScreen.SyncScreen
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.compose.viewmodels.SharedViewModel
import com.example.energo_monitoring.compose.viewmodels.SyncViewModel
import kotlinx.coroutines.launch

@Composable
fun SetupNavigation(clientInfoViewModel: ClientInfoViewModel,
                    sharedViewModel: SharedViewModel,
                    syncViewModel: SyncViewModel,
                    checksViewModel: ChecksViewModel){

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer = { scope.launch { drawerState.open() } }
    val closeDrawer = { scope.launch { drawerState.close() } }
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(onDestinationClicked = { route ->
                navController.navigate(route = route) {
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
                checksViewModel = checksViewModel,
                syncViewModel = syncViewModel,
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
    SetupNavigation(ClientInfoViewModel(), SharedViewModel(), SyncViewModel(), ChecksViewModel())
}