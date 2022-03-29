package com.example.energo_monitoring.compose.screens.mainMenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
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