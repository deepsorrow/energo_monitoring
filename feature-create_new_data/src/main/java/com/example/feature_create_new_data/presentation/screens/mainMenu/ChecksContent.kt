package com.example.feature_create_new_data.presentation.screens.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.example.feature_create_new_data.domain.SharedPreferencesManager
import com.example.feature_create_new_data.presentation.viewmodels.ChecksViewModel
import com.example.feature_create_new_data.presentation.viewmodels.SyncViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksContent(
    viewModel: ChecksViewModel,
    tabData: List<Pair<String, Painter>>,
    pagerState: PagerState
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = tabData.size, state = pagerState) { index ->
            when(index){
                0 -> {

                }
                1 -> {

                }
            }
        }
    }
}