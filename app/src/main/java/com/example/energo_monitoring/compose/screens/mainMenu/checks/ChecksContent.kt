package com.example.energo_monitoring.compose.screens.mainMenu.checks

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.data.api.ClientInfo
import com.example.energo_monitoring.data.db.ResultDataDatabase
import com.example.energo_monitoring.presentation.activities.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChecksContent(
    viewModel: ChecksViewModel,
    openCreateNewScreen: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.requestClientsInfo(context)
    }

    val clients by viewModel.clients.collectAsState()
    val onClientSelected = { dataId: Int ->
        val screenToOpen = ResultDataDatabase.getDatabase(context).resultDataDAO()
            .getOtherInfo(dataId).currentScreen
        val activity = when (screenToOpen) {
            1 -> Step1_ProjectPhotoActivity::class.java
            2 -> Step2_GoToPlaceActivity::class.java
            3 -> Step3_GeneralInspectionActivity::class.java
            4 -> Step4_DeviceInspectionActivity::class.java
            5 -> Step5_CheckLengthOfStraightLinesAreasActivity::class.java
            6 -> Step6_TemperatureCounterCharacteristicsActivity::class.java
            7 -> Step7_FinalPlacePhotosActivity::class.java
            else -> Step1_ProjectPhotoActivity::class.java
        }
        val intent = Intent(context, activity)
        intent.putExtra("dataId", dataId)
        context.startActivity(intent)
    }
    var isOpen by remember { mutableStateOf(false) }
    var currentClientInfo by remember { mutableStateOf(ClientInfo()) }

    AdditionalInfoDialog(
        isOpen = isOpen,
        onDismiss = { isOpen = !isOpen },
        clientInfo = currentClientInfo
    )

    val isRefreshing by viewModel.isRefreshing
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh(context) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Scaffold(
                content = {
                    LazyColumn {
                        items(items = clients) { item ->
                            CheckItem(
                                clientInfo = item.clientInfo,
                                onClick = { onClientSelected(it) },
                                progress = item.progress,
                                onInfoClicked = {
                                    isOpen = true
                                    currentClientInfo = item.clientInfo
                                }
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { openCreateNewScreen() },
                        backgroundColor = MaterialTheme.colors.primary,
                        content = {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun ChecksContentPreview() {
    ChecksContent(
        ChecksViewModel()
    ) {}
}