
@file:Suppress("unused", "CanSealedSubClassBeObject", "MemberVisibilityCanBePrivate")

package com.example.energo_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.screens.creatingNew1.ChooseFromListDialog
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

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
    goToNextScreen: (() -> Unit)? = null,
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            text = "Проверка поверочных документов",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            text = "Список добавленных приборов",
            fontSize = 17.sp
        )

        Column(
            modifier = Modifier
                // .fillMaxSize()
                // TODO: высота этого элемента
                .height(300.dp)
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            for (device in viewModel.devices) {
                // TODO: кликабельные пункты
                // логика для редактирования существующих устройств уже есть
                Text(
                    fontSize = 17.sp,
                    text = device.toString(),
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                )
            }
        }

        var openDialog by remember {
            mutableStateOf(false)
        }

        if (openDialog)
            ChooseFromListDialog(
                title = "",
                list = deviceTypes,
                openDialog = true,
                onCloseClicked = { openDialog = false },
                onConfirmClicked = {
                    openDialog = false

                    viewModel.deviceShouldBeAdded = true
                    viewModel.deviceInQuestion = it.factory()
                    viewModel.deviceInfoInQuestion = it

                    navController?.navigate("create_new_3_device")
                })

        // TODO: кнопка в виде плюса и поверх остального гуя
        // таким образом можно будет растянуть список добавленных устройств
        // на весь экран
        Button(
            onClick = { openDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Добавить новый прибор")
        }

        Button(
            onClick = goToNextScreen ?: {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Продолжить")
        }
    }
}

@Composable
@Preview
fun CreatingNew3ContentPreview() {
    CreatingNew3Content(ClientInfoViewModel())
}
