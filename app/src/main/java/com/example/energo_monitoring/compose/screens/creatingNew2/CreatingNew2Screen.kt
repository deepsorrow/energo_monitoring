package com.example.energo_monitoring.compose.screens.creatingNew2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew2Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    goToNextScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            CreatingNew2Content(viewModel = viewModel, goToNextScreen = goToNextScreen)
        }
    )
}

@Preview
@Composable
fun CreatingNew2ScreenPreview(){
    CreatingNew2Screen(ClientInfoViewModel(), { Job() }, {})
}
