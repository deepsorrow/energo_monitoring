package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Job

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksScreen(viewModel: ChecksViewModel, openDrawer: () -> Job, openCreateNewScreen: () -> Unit){
    Scaffold(
        topBar = {
            TopBar(
                title = "Проверки",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            ChecksContent(
                viewModel = viewModel,
                openCreateNewScreen = openCreateNewScreen
            )
        }
    )
}

@Preview
@Composable
fun PreviewChecksScreen(){
    ChecksScreen(viewModel = ChecksViewModel(), { Job(null) }, {})
}