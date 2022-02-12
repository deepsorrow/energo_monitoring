package com.example.feature_create_new_data.presentation.screens.syncScreen

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import com.example.feature_create_new_data.presentation.viewmodels.SyncViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SyncTabs(
    viewModel: SyncViewModel,
    tabData: List<Pair<String, Painter>>,
    pagerState: PagerState
) {
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    TabRow(selectedTabIndex = tabIndex) {
        tabData.forEach { tabPage ->
            val currentIndex = tabData.indexOf(tabPage)
            Tab(
                selected = currentIndex == tabIndex,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(currentIndex)
                    }
                },
                text = {
                    Text(text = tabPage.first)
                },
                icon = {
                    Icon(painter = tabPage.second, contentDescription = "Tab icon")
                }
            )
        }
    }
}