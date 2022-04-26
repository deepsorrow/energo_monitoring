package com.example.energo_monitoring.compose.screens.mainMenu

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksScreen(viewModel: ChecksViewModel, openDrawer: () -> Job, openCreateNewScreen: () -> Unit){
    val tabData = listOf(
        "Новые" to painterResource(id = R.drawable.ic_paste),
        "Начатые" to painterResource(id = R.drawable.ic_baseline_incomplete_circle_24),
    )
    val pagerState = rememberPagerState()

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
                tabData = tabData,
                pagerState = pagerState,
                openCreateNewScreen = openCreateNewScreen
            )
        },
        bottomBar = {
            CheckTabs(viewModel = viewModel, tabData = tabData, pagerState = pagerState)
        }
    )
}

@Preview
@Composable
fun PreviewChecksScreen(){
    ChecksScreen(viewModel = ChecksViewModel(), { Job(null) }, {})
}