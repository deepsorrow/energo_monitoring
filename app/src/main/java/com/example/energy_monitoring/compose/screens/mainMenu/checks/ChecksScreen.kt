package com.example.energy_monitoring.compose.screens.mainMenu.checks

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.energy_monitoring.compose.screens.TopBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Job

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksScreen(openDrawer: () -> Job, openCreateNewScreen: () -> Unit){
    Scaffold(
        topBar = {
            TopBar(
                title = "Проверки",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            ChecksContent(
                openCreateNewScreen = openCreateNewScreen
            )
        }
    )
}

@Preview
@Composable
fun PreviewChecksScreen(){
    ChecksScreen({ Job(null) }, {})
}