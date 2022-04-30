package com.example.energo_monitoring.compose.screens.syncScreen

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
import com.example.energo_monitoring.compose.domain.SharedPreferencesManager
import com.example.energo_monitoring.compose.viewmodels.SyncViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SyncContent(
    viewModel: SyncViewModel,
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (viewModel.isUpToDate())
                            Text(text = "Все проверки синхронизированы!")
                        else
                            Text(text = "Ожидает синхронизации: 1")

                        OutlinedButton(onClick = { }) {
                            Text(text = "Синхронизироовать")
                        }
                    }
                }
                1 -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val lastUpdateDate = SharedPreferencesManager
                            .getLastClientsInfoUpdateDate(LocalContext.current)
                        if(lastUpdateDate == null || lastUpdateDate.isEmpty())
                            Text(text = "База номеров договоров еще не была загружена")
                        else
                            Text(modifier = Modifier.fillMaxWidth(),
                                text = "База номеров договоров актуальна на: $lastUpdateDate")

                        OutlinedButton(
                            onClick = { }) {
                            Text(text = "Синхронизироовать")
                        }
                    }
                }
            }
        }
    }
}