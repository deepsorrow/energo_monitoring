package com.example.energo_monitoring.compose.screens.mainMenu.checks

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.checks.data.api.ClientDataBundle
import com.example.energo_monitoring.checks.data.api.ClientInfo
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
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
    var showProgressBar by remember { mutableStateOf(false) }

    val requestClientsInfo = {
        showProgressBar = true
        viewModel.requestClientsInfo(context) {
            showProgressBar = false
        }
    }

    LaunchedEffect(key1 = true) {
        requestClientsInfo()
    }

    val clients by viewModel.clients.collectAsState()

    val loadInfo =
        { dataId: Int, clientName: String, needToOpenCheck: Boolean, onComplete: (ClientDataBundle) -> Unit ->
            showProgressBar = true
            viewModel.loadInfo(
                dataId = dataId,
                onComplete = { response ->
                    showProgressBar = false
                    if (response == null) {
                        Toast.makeText(context,"Произошла ошибка! Код ошибки: 101.",  Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.saveDataFromCloud(context, dataId, response)
                        if (needToOpenCheck) {
                            viewModel.openCheck(context, clientName, dataId)
                        }
                        onComplete(response)
                    }
                },
                onError = {
                    showProgressBar = false
                    Toast.makeText(context,"Не удалось получить данные! Ошибка: $it", Toast.LENGTH_LONG).show()
                }
            )
        }

    val syncInfo = { dataId: Int, clientName: String ->
        loadInfo(dataId, clientName, false) { cloudData ->
            val localData = ResultDataDatabase.getDatabase(context).resultDataDAO().getData(dataId)
            if (localData == null || localData.otherInfo.dataId == 0
                || cloudData.version > localData.otherInfo.localVersion) {
                viewModel.saveDataFromCloud(context, dataId, cloudData)
                requestClientsInfo()
            } else {
                // TODO: sendDataToCloud
            }
        }
    }

    val onClientSelected = { dataId: Int, clientName: String ->
        val otherInfo = ResultDataDatabase.getDatabase(context).resultDataDAO().getOtherInfo(dataId)
        var currentScreen = 1
        if (otherInfo != null) {
            currentScreen = otherInfo.currentScreen
            viewModel.openCheck(context, clientName, dataId)
        } else {
            loadInfo(dataId, clientName, true) {}
        }
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
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh(context) },
    ) {
        Column {
            AnimatedVisibility(
                visible = showProgressBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "Загрузка данных с облака...",
                        fontSize = 18.sp
                    )
                }
            }
            Scaffold(
                content = {
                    if (clients.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_hourglass_empty_24),
                                contentDescription = "Проверок нет",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Созданных проверок пока нет.\nСоздайте новую или попробуйте обновить потянув сверху.",
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        }
                    } else {
                        LazyColumn {
                            items(items = clients) { item ->
                                CheckItem(
                                    clientInfo = item.clientInfo,
                                    onClick = {
                                        onClientSelected(
                                            item.clientInfo.dataId,
                                            item.clientInfo.name
                                        )
                                    },
                                    progress = item.progress,
                                    onInfoClicked = {
                                        isOpen = true
                                        currentClientInfo = item.clientInfo
                                    },
                                    onSyncClicked = {
                                        syncInfo(item.clientInfo.dataId, item.clientInfo.name)
                                    },
                                    syncStatus = item.synced
                                )
                            }
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
                                    painter = painterResource(id = R.drawable.ic_add),
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