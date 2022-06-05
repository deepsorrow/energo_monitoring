
@file:Suppress("unused", "CanSealedSubClassBeObject", "MemberVisibilityCanBePrivate")

package com.example.energy_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.screens.creatingNew1.ChooseFromListDialog
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import java.lang.ref.WeakReference
import java.math.BigDecimal
import kotlin.math.roundToInt

private val deviceTypes: List<IDeviceInfo<*>> = listOf(
    HeatCalculator.Companion,
    FlowConverter.Companion,
    TemperatureConverter.Companion,
    PressureConverter.Companion,
    //Counter.Companion,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreatingNew3Content(
    viewModel: ClientInfoViewModel,
    goToNextScreen: (() -> Unit) = {},
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
                .padding(top = 20.dp, start = 15.dp, end = 15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val swipeStates = remember {
                mutableStateListOf<Pair<WeakReference<AbstractDevice<*>>, SwipeableState<Int>>>()
            }

            val sizePx = with(LocalDensity.current) { (-500).dp.toPx() }
            val anchors = mapOf(sizePx to 1, 0f to 0)
            var change by remember {
                mutableStateOf(0)
            }

            val iterator = viewModel.devices.iterator()

            for (device in iterator) {
                var findSwipe = swipeStates.firstOrNull { it.first.get() === device }?.second

                if (findSwipe == null) {
                    findSwipe = rememberSwipeableState(initialValue = 0)
                    swipeStates.add(WeakReference(device) to findSwipe)
                }

                if (findSwipe.currentValue == 1) {
                    iterator.remove()
                    change++
                    continue
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .clickable {
                            viewModel.deviceShouldBeAdded = false
                            viewModel.deviceInQuestion = device
                            viewModel.deviceInfoInQuestion = device.info

                            navController?.navigate("create_new_3_device")
                        }
                        .swipeable(
                            state = findSwipe,
                            anchors = anchors,
                            orientation = Orientation.Horizontal
                        )
                        .offset { IntOffset(findSwipe.offset.value.roundToInt(), 0) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_device_hub_24),
                        contentDescription = device.info.nominative)

                    Text(
                        fontSize = 17.sp,
                        text = device.toString(),
                        modifier = Modifier
                            .padding(start = 10.dp),
                    )
                }
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
            onClick = goToNextScreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Сохранить акт на сервере")
        }
    }
}

@Composable
@Preview
fun CreatingNew3ContentPreview() {
    CreatingNew3Content(ClientInfoViewModel().also {
        it.devices.add(HeatCalculator().also {
            it.deviceName = "Имя"
            it.deviceNumber = 15
            it.installationPlace = 188
        })

        it.devices.add(FlowConverter().also {
            it.deviceName = "Имя"
            it.deviceNumber = 15
            it.diameter = BigDecimal("0.4")
            it.weight = BigDecimal("200")
        })

        it.devices.add(FlowConverter().also {
            it.deviceName = "Имя"
            it.deviceNumber = 15
            it.diameter = BigDecimal("0.4")
            it.weight = BigDecimal("200")
        })

        it.devices.add(FlowConverter().also {
            it.deviceName = "Имя"
            it.deviceNumber = 15
            it.diameter = BigDecimal("0.4")
            it.weight = BigDecimal("200")
        })
    })
}
