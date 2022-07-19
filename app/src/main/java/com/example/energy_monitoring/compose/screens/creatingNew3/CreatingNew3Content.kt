@file:Suppress("unused", "CanSealedSubClassBeObject", "MemberVisibilityCanBePrivate")

package com.example.energy_monitoring.compose.screens.creatingNew3

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.domain.DropDownMenu
import com.example.energy_monitoring.compose.screens.DataGroup
import com.example.energy_monitoring.compose.screens.FloatingPlusButton
import com.example.energy_monitoring.compose.screens.NoDataPlaceholder
import com.example.energy_monitoring.compose.screens.creatingNew1.ChooseFromListDialog
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel

private val deviceTypes: List<IDeviceInfo<*>> = listOf(
    HeatCalculator.Companion,
    FlowConverter.Companion,
    TemperatureConverter.Companion,
    PressureConverter.Companion,
    Counter.Companion,
)

@Composable
fun CreatingNew3Content(
    viewModel: ClientInfoViewModel,
    sendResults: ((context: Context) -> Unit) = {},
    navController: NavController? = null
) {
    val context = LocalContext.current
    var openDeviceCreateDialog by remember { mutableStateOf(false) }
    var openPickFromCatalogDialog by remember { mutableStateOf(false) }

    val items = remember { mutableListOf<DropDownMenu>() }
    LaunchedEffect(key1 = true) {
        DropDownDeviceActions.values().forEach { items.add(DropDownMenu(it.title, it.icon)) }
    }

    Column {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        DataGroup("Устройства", items) {
            when (it.title) {
                DropDownDeviceActions.PICK_FROM_CATALOG.title -> openPickFromCatalogDialog = true
            }
        }
        Box(contentAlignment = Alignment.Center) {
            if (viewModel.devices.isEmpty()) {
                NoDataPlaceholder(
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                    text = "Устройств пока нет\n" +
                            "Создайте новое или выберите из каталога",
                    iconRes = R.drawable.ic_texture
                )
            }
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 500.dp, max = 550.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .border(1.dp, Color.LightGray)
                ) {
                    itemsIndexed(viewModel.devices) { index, device ->
                        if (index == 0) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .padding(start = 10.dp, end = 10.dp)
                                .clickable {
                                    viewModel.deviceShouldBeAdded = false
                                    viewModel.deviceInQuestion = device
                                    viewModel.deviceInfoInQuestion = device.info

                                    navController?.navigate("create_new_3_device")
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_device_hub_24),
                                contentDescription = device.info.nominative
                            )

                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Text(
                                    fontSize = 17.sp,
                                    text = device.toString()
                                )
                                Text(
                                    text = device.info.nominative,
                                    color = Color.LightGray
                                )
                            }
                        }

                        if (index != viewModel.devices.count() - 1) {
                            Divider(
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                                    .height(0.2.dp)
                                    .background(color = Color.LightGray)
                            )
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }
                }

                FloatingPlusButton(Modifier.padding(end = 25.dp, bottom = 5.dp)) { openDeviceCreateDialog = true }
            }
        }

        Button(
            onClick = {
                sendResults(context)
                viewModel.dispose(context, false)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
        ) {
            Text(text = "Завершить создание акта")
        }
    }

    if (openDeviceCreateDialog) {
        ChooseFromListDialog(
            title = "Выберите тип устройства",
            list = deviceTypes,
            openDialog = true,
            showSearch = false,
            onCloseClicked = { openDeviceCreateDialog = false },
            onConfirmClicked = {
                openDeviceCreateDialog = false

                viewModel.deviceShouldBeAdded = true
                viewModel.deviceInQuestion = it.factory()
                viewModel.deviceInfoInQuestion = it

                navController?.navigate("create_new_3_device")
            }
        )
    }
}

@Composable
@Preview
fun CreatingNew3ContentPreview() {
//    CreatingNew3Content(ClientInfoViewModel().also {
//        it.devices.add(HeatCalculator().also {
//            it.deviceName = "Имя"
//            it.deviceNumber = 15
//            it.installationPlace = 188
//        })
//
//        it.devices.add(FlowConverter().also {
//            it.deviceName = "Имя"
//            it.deviceNumber = 15
//            it.diameter = BigDecimal("0.4")
//            it.weight = BigDecimal("200")
//        })
//
//        it.devices.add(FlowConverter().also {
//            it.deviceName = "Имя"
//            it.deviceNumber = 15
//            it.diameter = BigDecimal("0.4")
//            it.weight = BigDecimal("200")
//        })
//
//        it.devices.add(FlowConverter().also {
//            it.deviceName = "Имя"
//            it.deviceNumber = 15
//            it.diameter = BigDecimal("0.4")
//            it.weight = BigDecimal("200")
//        })
//    })
}
