package com.example.energy_monitoring.compose.screens.creatingNew3

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.energy_monitoring.compose.DrawerScreens
import com.example.energy_monitoring.compose.data.api.DueDateResponse
import com.example.energy_monitoring.compose.data.api.ModelDetailed
import com.example.energy_monitoring.compose.screens.*
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CreatingNew3Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    sendResults: ((context: Context) -> Unit) = {},
    navController: NavController,
) {
    val context = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        bottomBar = {
            ExitConfirmAlertDialog(
                showAlertDialog = showAlertDialog,
                onConfirm = {
                    viewModel.dispose(context)
                    navController.popBackStack(DrawerScreens.Checks.route, false)
                },
                onDismiss = { showAlertDialog = false }
            )
            CreatingNewNavBottom(navController)
        },
        content = {
            CreatingNew3Content(
                viewModel = viewModel,
                navController = navController,
                sendResults = sendResults,
            )
        }
    )

    BackHandlerConfirmExit(navController, showAlertDialog) { showAlertDialog = true }
}

// TODO: Снапшоттинг состояния устройства, т.е. когда пользователь хочет отменить изменения
// в уже существующем устройстве, мы можем в прямом смысле их отменить.
@Composable
fun CreatingNewDeviceScreen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController? = null
) {
    var searchJob: Job? = remember { Job() }
    val scope = rememberCoroutineScope()
    var certificateNum by remember { mutableStateOf("") }
    var certificateCheckResult by remember { mutableStateOf(DueDateResponse()) }
    val info = viewModel.deviceInfoInQuestion ?: throw IllegalStateException("Device info is absent in ClientInfoViewModel")
    // val info = viewModel.deviceInfoInQuestion ?: return
    val device = viewModel.deviceInQuestion ?: throw IllegalStateException("Device is absent in ClientInfoViewModel")

    Scaffold(
        topBar = {
            TopBar(
                title = if (viewModel.deviceShouldBeAdded) "Создание нового ${info.genitive}" else "Редактирование ${info.genitive}",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DataGroup(title = "Характеристики устройства")
                device.compose()
                
                Spacer(modifier = Modifier.height(10.dp))
                
                DataGroup(title = "Проверка даты окончания поверки")
                TextField(
                    modifier = Modifier
                        .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text(text = "Номер свидетельства")
                    },
                    value = certificateNum,
                    onValueChange = {
                        certificateNum = it
                        searchJob?.cancel()
                        if (it.length > 3) {
                            certificateCheckResult = DueDateResponse(null, "Поиск...")
                            searchJob = scope.launch {
                                delay(500)
                                searchDueDate(it) { result -> certificateCheckResult = result }
                            }
                        }
                    },
                )
                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (certificateNum.length > 3) {
                        if (certificateCheckResult.result == "Поиск...") {
                            CircularProgressIndicator(color = Color.DarkGray, strokeWidth = 1.dp)
                        }
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = certificateCheckResult.result.orEmpty(),
                            fontSize = 17.sp,
                            color = if (certificateCheckResult.isSuccess == null)
                                Color.DarkGray
                            else if (certificateCheckResult.isSuccess)
                                Color(85, 139, 47, 255)
                            else
                                Color(204, 86, 86, 255)
                        )
                    }
                }
                
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                    onClick = {
                        if (viewModel.deviceShouldBeAdded) {
                            viewModel.devices.add(device)
                        }

                        // TODO: а как?
                        // viewModel.deviceInfoInQuestion = null
                        // viewModel.deviceInQuestion = null

                        if (navController?.popBackStack() != true) {
                            navController?.navigate("create_new_3")
                        }
                    }
                ) {
                    Text(text = "Сохранить")
                }
            }
        }
    )
}

fun searchDueDate(certificateNum: String, onResult: (DueDateResponse) -> Unit) {
    val gson = Gson()

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://fgis.gost.ru/fundmetrology/cm/xcdb/vri/select?fq=$certificateNum&q=*&fl=mi.mitnumber,valid_date")
        .build()

    client.newCall(request).enqueue(object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            onResult(DueDateResponse(false, "Ошибка: $e"))
        }

        override fun onResponse(call: Call, response: Response) {
            val entity = response.body
            if (entity != null) {
                val objResult = gson.fromJson(entity.string(), ModelDetailed::class.java)
                val count = objResult.response.numFound
                if (count == 0) {
                    onResult(DueDateResponse(false, "Свидетельств не найдено"))
                } else if (count > 1) {
                    onResult(DueDateResponse(false, "Найдено $count свидетельств, пожалуйста уточните"))
                } else {
                    var validDate = objResult.response.docs[0].validDate
                    validDate = validDate.replace("T", " ")
                    validDate = validDate.replace("Z", "")
                    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("RU")).parse(validDate)
                    if (date != null) {
                        val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale("RU")).format(date)
                        onResult(DueDateResponse(true, formattedDate))
                    } else {
                        onResult(DueDateResponse(false, "Ошибка, код ошибки: 1"))
                    }
                }
            }
        }
    })
}

@Preview
@Composable
fun CreatingNewDeviceScreenPreview() {
//    val view = ClientInfoViewModel())
//
//    view.deviceShouldBeAdded = true
//    view.deviceInQuestion = Counter()
//    view.deviceInfoInQuestion = Counter.Companion
//
//    CreatingNewDeviceScreen(view, { Job() })
}
