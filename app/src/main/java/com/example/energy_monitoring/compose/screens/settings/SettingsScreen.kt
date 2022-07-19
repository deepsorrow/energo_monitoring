package com.example.energy_monitoring.compose.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.checks.ui.utils.Settings
import com.example.energy_monitoring.compose.screens.DataGroup
import com.example.energy_monitoring.compose.screens.TopBar
import com.example.energy_monitoring.compose.screens.mainMenu.checks.ChecksContent
import com.example.energy_monitoring.compose.viewmodels.SettingsVM
import kotlinx.coroutines.Job

@Composable
fun SettingsScreen(
    openDrawer: () -> Job,
    viewModel: SettingsVM = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Настройки",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = viewModel.serverIp,
                    onValueChange = {
                        viewModel.onChanged()
                        viewModel.serverIp = it
                        Settings.server_ip = it
                    },
                    label = {
                        Text(
                            text = "Ip-адрес сервера"
                        )
                    })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = viewModel.serverPort,
                    onValueChange = {
                        viewModel.onChanged()
                        viewModel.serverPort = it
                        Settings.server_port = it
                    },
                    label = {
                        Text(
                            text = "Порт сервера"
                        )
                    })
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.ping() }) {
                    Text(text = "Проверить подключение")
                }
                if (viewModel.connectionSet == true) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Соединение успешно установлено!",
                        color = Color(46, 125, 50, 255)
                    )
                } else if (viewModel.connectionSet == false) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Не удалось соединиться с сервером!",
                        color = Color(198, 40, 40, 255)
                    )
                }
            }
        }
    )
}