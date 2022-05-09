package com.example.energo_monitoring.compose.screens.creatingNew3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.energo_monitoring.compose.screens.TopBar
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun CreatingNew3Screen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    goToNextScreen: (() -> Unit)? = null,
    navController: NavController? = null,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Создание нового акта",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            CreatingNew3Content(
                viewModel = viewModel,
                navController = navController,
                goToNextScreen = goToNextScreen,
            )
        }
    )
}

@Preview
@Composable
fun CreatingNew3ScreenPreview() {
    CreatingNew3Screen(ClientInfoViewModel(), { Job() })
}

// TODO: Снапшоттинг состояния устройства, т.е. когда пользователь хочет отменить изменения
// в уже существующем устройстве, мы можем в прямом смысле их отменить.
@Composable
fun CreatingNewDeviceScreen(
    viewModel: ClientInfoViewModel,
    openDrawer: () -> Job,
    navController: NavController? = null
) {
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
                device.compose()

                Button(
                    onClick = {
                        if (viewModel.deviceShouldBeAdded) {
                            viewModel.devices.add(device)
                        }

                        // TODO: а как?
                        // viewModel.deviceInfoInQuestion = null
                        // viewModel.deviceInQuestion = null

                        //if (navController?.popBackStack() != true) {
                            navController?.navigate("create_new_3")
                        //}
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    // enabled = device.isValid
                ) {
                    Text(text = "Сохранить")
                }
            }
        }
    )
}

@Preview
@Composable
fun CreatingNewDeviceScreenPreview() {
    val view = ClientInfoViewModel()

    view.deviceShouldBeAdded = true
    view.deviceInQuestion = Counter()
    view.deviceInfoInQuestion = Counter.Companion

    CreatingNewDeviceScreen(view, { Job() })
}
