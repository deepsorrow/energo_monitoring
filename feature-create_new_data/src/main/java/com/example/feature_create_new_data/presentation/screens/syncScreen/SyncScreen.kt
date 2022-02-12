package com.example.feature_create_new_data.presentation.screens.syncScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.feature_create_new_data.R
import com.example.feature_create_new_data.domain.SharedPreferencesManager
import com.example.feature_create_new_data.presentation.screens.TopBar
import com.example.feature_create_new_data.presentation.viewmodels.SyncViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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