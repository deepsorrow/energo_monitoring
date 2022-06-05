package com.example.energy_monitoring.compose.screens.creatingNew2

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.energy_monitoring.compose.screens.CreatingNewNavBottom
import com.example.energy_monitoring.compose.screens.TopBar
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew2Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController?
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        bottomBar = {
            if (navController != null)
                CreatingNewNavBottom(navController)
        },
        content = {
            CreatingNew2Content(viewModel = viewModel)
        }
    )
}

@Preview
@Composable
fun CreatingNew2ScreenPreview(){
    CreatingNew2Screen(ClientInfoViewModel(), { Job() }, null)
}
