package com.example.energy_monitoring.compose.screens.creatingNew2

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.energy_monitoring.compose.DrawerScreens
import com.example.energy_monitoring.compose.screens.BackHandlerConfirmExit
import com.example.energy_monitoring.compose.screens.CreatingNewNavBottom
import com.example.energy_monitoring.compose.screens.ExitConfirmAlertDialog
import com.example.energy_monitoring.compose.screens.TopBar
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew2Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController
) {
    val context = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        bottomBar = {
            CreatingNewNavBottom(navController)
        },
        content = {
            ExitConfirmAlertDialog(
                showAlertDialog = showAlertDialog,
                onConfirm = {
                    viewModel.dispose(context)
                    navController.popBackStack(DrawerScreens.Checks.route, false)
                },
                onDismiss = { showAlertDialog = false }
            )
            CreatingNew2Content(viewModel = viewModel)
        }
    )

    BackHandlerConfirmExit(navController, showAlertDialog) { showAlertDialog = true }
}
