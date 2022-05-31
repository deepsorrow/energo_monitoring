package com.example.energo_monitoring.compose.screens.creatingNew2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.screens.CreatingNewNavBottom
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
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
            Box(modifier = Modifier.padding(it)) {
                CreatingNew2Content(viewModel = viewModel)
            }
        }
    )
}

@Preview
@Composable
fun CreatingNew2ScreenPreview(){
    CreatingNew2Screen(ClientInfoViewModel(), { Job() }, null)
}
