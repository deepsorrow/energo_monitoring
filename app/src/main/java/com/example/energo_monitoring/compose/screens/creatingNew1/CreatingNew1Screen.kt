package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.screens.CreatingNewNavBottom
import com.example.energo_monitoring.compose.screens.ExitConfirmAlertDialog
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew1Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController
) {
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
                onConfirm = { navController.navigate("checks") },
                onDismiss = { showAlertDialog = false }
            )
            CreatingNew1Content(viewModel = viewModel)
        }
    )

    BackHandler {
        // Если предупреждение уже открыто, то можно выходить
        if (showAlertDialog) {
            navController.navigate("checks")
        } else {
            showAlertDialog = true
        }
    }
}
