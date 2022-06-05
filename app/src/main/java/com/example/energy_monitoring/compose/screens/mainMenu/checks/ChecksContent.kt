package com.example.energy_monitoring.compose.screens.mainMenu.checks

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energy_monitoring.checks.data.api.ClientDataBundle
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.db.ResultDataDatabase
import com.example.energy_monitoring.compose.LoadingIndicator
import com.example.energy_monitoring.compose.data.SyncStatus
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ChecksContent(
    viewModel: ChecksViewModel = hiltViewModel(),
    openCreateNewScreen: () -> Unit,
) {
    val context = LocalContext.current
    var showProgressBar by remember { mutableStateOf(false) }
    var loadingText by remember { mutableStateOf("Загрузка данных с облака...") }

    val requestClientsInfo = {
        showProgressBar = true
        viewModel.requestClientsInfo(context) {
            showProgressBar = false
        }
    }

    LaunchedEffect(key1 = true) {
        requestClientsInfo()
    }

    val loadInfo =
        { dataId: Int, clientName: String, needToOpenCheck: Boolean, onComplete: (ClientDataBundle) -> Unit ->
            showProgressBar = true
            viewModel.loadInfo(
                dataId = dataId,
                onComplete = { response ->
                    loadingText = "Загрузка данных с облака..."
                    showProgressBar = false
                    if (response == null) {
                        Toast.makeText(context,"Произошла ошибка! Код ошибки: 101.",  Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.saveDataFromCloud(context, dataId, response)
                        if (needToOpenCheck) {
                            viewModel.openCheck(context, clientName, 1, dataId)
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

    val syncInfo = { dataId: Int ->
        viewModel.loadInfo(
            dataId = dataId,
            onComplete = { cloudData ->
                if (cloudData != null) {
                    showProgressBar = true
                    val localData =
                        ResultDataDatabase.getDatabase(context).resultDataDAO().getData(dataId)
                    if (localData == null || localData.otherInfo!!.dataId == 0 || cloudData.version > localData.otherInfo!!.localVersion) {
                        loadingText = "Загрузка данных с облака..."
                        viewModel.saveDataFromCloud(context, dataId, cloudData)
                        requestClientsInfo()
                    } else {
                        loadingText = "Отправка данных..."
                        viewModel.sendDataToCloud(
                            localData = localData,
                            onComplete = {
                                requestClientsInfo()
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                    "Не удалось отправить данные! Ошибка: $it",
                                    Toast.LENGTH_LONG
                                ).show()
                                showProgressBar = false
                            })
                    }
                }
            },
            onError = {
                Toast.makeText(
                    context,
                    "Не удалось отправить данные! Ошибка: $it",
                    Toast.LENGTH_LONG
                ).show()
                showProgressBar = false
            }
        )
    }

    val onClientSelected = { dataId: Int, clientName: String ->
        val otherInfo = ResultDataDatabase.getDatabase(context).resultDataDAO().getOtherInfo(dataId)
        val currentScreen = otherInfo?.currentScreen ?: 1
        if (otherInfo != null) {
            viewModel.openCheck(context, clientName, currentScreen, dataId)
        } else {
            loadInfo(dataId, clientName, true) {}
        }
    }

    var confirmLocalDeletePopup by remember { mutableStateOf(false) }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> { requestClientsInfo() }
            else                      -> {  }
        }
    }
    var lastSyncStatus by remember { mutableStateOf<SyncStatus?>(null) }
    var onLocalDeleteConfirm by remember { mutableStateOf({}) }

    ConfirmLocalDeleteDialog(
        showAlertDialog = confirmLocalDeletePopup,
        syncStatus = lastSyncStatus,
        onConfirm = onLocalDeleteConfirm,
        onDismiss = { confirmLocalDeletePopup = false }
    )

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(viewModel.isRefreshing),
        onRefresh = { viewModel.refresh(context) },
    ) {
        Column {
            LoadingIndicator(isVisible = showProgressBar, loadingText)
            Scaffold(
                content = {
                    if (viewModel.clients.isEmpty()) {
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
                                text = "Созданных проверок пока нет\nСоздайте новую или попробуйте обновить потянув сверху",
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        }
                    } else {
                        LazyColumn {
                            items(items = viewModel.clients) { item ->
                                CheckItem(
                                    clientInfo = item.clientInfo,
                                    onClick = {
                                        onClientSelected(
                                            item.clientInfo.dataId,
                                            item.clientInfo.name
                                        )
                                    },
                                    onActionClick = { selectedAction: DropDownClientActions, clientInfo: ClientInfo ->
                                        when (selectedAction) {
                                            DropDownClientActions.SYNC -> {
                                                syncInfo(item.clientInfo.dataId)
                                            }
                                            DropDownClientActions.REMOVE_LOCALLY -> {
                                                confirmLocalDeletePopup = true
                                                lastSyncStatus = item.syncStatus
                                                onLocalDeleteConfirm = { viewModel.onActionClicked(selectedAction, context, clientInfo) }
                                            }
                                            DropDownClientActions.REMOVE_GLOBALLY -> {

                                            }
                                        }
                                    },
                                    progress = item.progress,
                                    syncStatus = item.syncStatus,
                                    occupiedSpace = viewModel.getOccupiedSpace(context, item.clientInfo.dataId)
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

@Composable
private fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Preview
@Composable
fun ChecksContentPreview() {
    ChecksContent {}
}