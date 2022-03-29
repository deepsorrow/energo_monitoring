package com.example.energo_monitoring.compose.screens.syncScreen

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.SyncViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job
import com.example.energo_monitoring.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SyncScreen(viewModel: SyncViewModel, openDrawer: () -> Job) {

    val tabData = listOf(
        "Проверки" to painterResource(id = R.drawable.ic_baseline_content_paste_go_24),
        "Договоры" to painterResource(id = R.drawable.ic_baseline_list_alt_24),
    )
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopBar("Синхронизация") { openDrawer() }
        },
        content = {
            SyncContent(viewModel = viewModel, tabData = tabData, pagerState = pagerState)
        },
        bottomBar = {
            SyncTabs(viewModel = viewModel, tabData = tabData, pagerState = pagerState)
        }
    )
}

@Composable
@Preview
fun PreviewSyncScreen(){
    SyncScreen(SyncViewModel(), { Job(null) })
}