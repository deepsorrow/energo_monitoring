package com.example.energo_monitoring.compose.screens.mainMenu

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.screens.mainMenu.checks.CheckItem
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.presentation.activities.ProjectPhotoActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksContent(
    viewModel: ChecksViewModel,
    tabData: List<Pair<String, Painter>>,
    pagerState: PagerState,
    openCreateNewScreen: () -> Unit,
){
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getClients(context)
    }

    val clients by viewModel.clients.collectAsState()

    val onClientSelected = { dataId: Int ->
        val intent = Intent(context, ProjectPhotoActivity::class.java)
        intent.putExtra("dataId", dataId)
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(count = tabData.size, state = pagerState) { index ->
            when(index){
                0 -> {
                    Scaffold(content = {
                        LazyColumn {
                            items(items = clients) { item ->
                                CheckItem(clientInfo = item, onClick = { onClientSelected(it) })
                            }
                        }
                    }, floatingActionButton = {
                        FloatingActionButton(
                            onClick = { openCreateNewScreen() },
                            backgroundColor = Color(74, 20, 140, 255),
                            content = {
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    })

                }
                1 -> {

                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun ChecksContentPreview(){
    ChecksContent(
        ChecksViewModel(),
        listOf(
            "Проверки" to painterResource(id = R.drawable.ic_paste),
            "Начатые" to painterResource(id = R.drawable.ic_baseline_incomplete_circle_24)
        ),
        PagerState(),
        {}
    )
}