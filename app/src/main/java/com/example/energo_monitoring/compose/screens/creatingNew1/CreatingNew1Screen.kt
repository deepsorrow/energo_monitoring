package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.screens.CreatingNewNavBottom
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew1Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController
) {
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
            CreatingNew1Content(viewModel = viewModel)
        }
    )
}
