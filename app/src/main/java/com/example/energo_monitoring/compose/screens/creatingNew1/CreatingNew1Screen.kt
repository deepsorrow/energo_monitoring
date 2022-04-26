package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.screens.mainMenu.ChecksContent
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew1Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job
){
    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            CreatingNew1Content(viewModel = viewModel)
        }
    )
}