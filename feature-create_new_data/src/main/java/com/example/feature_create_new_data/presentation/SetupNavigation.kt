package com.example.feature_create_new_data.presentation.screens.creatingNew1

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature_create_new_data.data.DrawerScreens
import com.example.feature_create_new_data.presentation.screens.TopBar
import com.example.feature_create_new_data.presentation.screens.drawer.Drawer
import com.example.feature_create_new_data.presentation.screens.mainMenu.ChecksScreen
import com.example.feature_create_new_data.presentation.screens.syncScreen.SyncScreen
import com.example.feature_create_new_data.presentation.viewmodels.ChecksViewModel
import com.example.feature_create_new_data.presentation.viewmodels.ClientInfoViewModel
import com.example.feature_create_new_data.presentation.viewmodels.SharedViewModel
import com.example.feature_create_new_data.presentation.viewmodels.SyncViewModel
import kotlinx.coroutines.launch

@Composable
fun SetupNavigation(clientInfoViewModel: ClientInfoViewModel,
                    sharedViewModel: SharedViewModel,
                    syncViewModel: SyncViewModel,
                    checksViewModel: ChecksViewModel){

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    val closeDrawer = {
        scope.launch {
            drawerState.close()
        }
    }

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
            startDestination = DrawerScreens.Checks.route
        ) {
            composable(DrawerScreens.Checks.route) {
                ChecksScreen(viewModel = checksViewModel, openDrawer = openDrawer)
            }

            composable(DrawerScreens.Sync.route) {
                SyncScreen(viewModel = syncViewModel, openDrawer = openDrawer)
            }
        }
    }
}

@Composable
@Preview
private fun MainMenuPreview(){
    SetupNavigation(ClientInfoViewModel(), SharedViewModel(), SyncViewModel(), ChecksViewModel())
}