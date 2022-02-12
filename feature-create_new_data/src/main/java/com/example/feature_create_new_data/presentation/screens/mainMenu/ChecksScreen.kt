package com.example.feature_create_new_data.presentation.screens.mainMenu

import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.feature_create_new_data.R
import com.example.feature_create_new_data.presentation.screens.TopBar
import com.example.feature_create_new_data.presentation.viewmodels.ChecksViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksScreen(viewModel: ChecksViewModel, openDrawer: () -> Job){
    val tabData = listOf(
        "Новые" to painterResource(id = R.drawable.ic_baseline_content_paste_go_24),
        "Начатые" to painterResource(id = R.drawable.ic_baseline_incomplete_circle_24),
    )
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = { TopBar(
            title = "Проверки",
            onNavigationIconClicked = { openDrawer() }
        ) },
        content = {
            ChecksContent(viewModel = viewModel, tabData = tabData, pagerState = pagerState)
        },
        bottomBar = {
            CheckTabs(viewModel = viewModel, tabData = tabData, pagerState = pagerState)
        }
    )
}

@Preview
@Composable
fun PreviewChecksScreen(){
    ChecksScreen(viewModel = ChecksViewModel(), { Job(null) })
}