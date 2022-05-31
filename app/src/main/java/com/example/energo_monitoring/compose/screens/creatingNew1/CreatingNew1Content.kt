package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.R

@Composable
fun CreatingNew1Content(viewModel: ClientInfoViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = "Акт ввода в коммерческий учёт",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        var alertAutoUpdateFields by remember { mutableStateOf(false) }

        if (alertAutoUpdateFields) {
            AlertDialog(
                text = {
                    Text(text = "Поля были изменены вручную. Обновить их в соответствии с новым контрактом автоматически?")
                },

                onDismissRequest = { alertAutoUpdateFields = false },

                confirmButton = {
                    Text(
                        color = Color(0xFF018786),
                        text = "Да",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                alertAutoUpdateFields = false
                                viewModel.agreementNumber?.let(viewModel::autoUpdate)
                            }
                            .padding(10.dp)
                    )
                },

                dismissButton = {
                    Text(
                        color = Color(0xFF018786),
                        text = "Нет",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                alertAutoUpdateFields = false
                            }
                            .padding(10.dp)
                    )
                },

                title = {
                    Text("Автоматическое обновление полей")
                },
            )
        }

        AgreementNumTextField(
            placeholder = "Номер договора абонента",
            viewModel.agreementNumber,
            viewModel.availableClientsInfo
        ) {
            viewModel.agreementNumber = it

            if (viewModel.canAutoUpdate) {
                // Ничего не было введено пользователем
                // выставляем всё автоматически
                viewModel.autoUpdate(it)
            } else {
                alertAutoUpdateFields = true
            }
        }

        CreatingTextField(placeholder = "Название контрагента", viewModel.name) {
            viewModel.name = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Адрес УУТЭ контрагента", viewModel.addressUUTE) {
            viewModel.addressUUTE = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Представитель абонента", viewModel.representativeName) {
            viewModel.representativeName = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Телефон представителя абонента", viewModel.phoneNumber) {
            viewModel.phoneNumber = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Электронный адрес абонента", viewModel.email) {
            viewModel.email = it
            viewModel.modifiedByUserOnce = true
        }

        ServingOrganizationTextField(
            placeholder = "Обслуживающая организация",
            viewModel.servingOrganization,
            viewModel.availableServingOrganizations
        ) {
            viewModel.servingOrganization = it
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 10.dp, top = 15.dp)
        ) {
            Checkbox(checked = viewModel.matchesConditions, onCheckedChange = {
                viewModel.matchesConditions = it
            })

            Text(
                text = "Соответствие проекта нормативным требованиям",
                fontSize = 17.sp
            )
        }

        CreatingLongTextField(placeholder = "Комментарий", viewModel.commentary) {
            viewModel.commentary = it
        }

        if (viewModel.agreementFound) {
            Row(
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "Задолженность: ",
                    fontSize = 16.sp
                )
                Text(
                    text = "<не выбран номер договора...>",
                    fontSize = 16.sp
                )
            }
        } else {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Filled.Info,
                    contentDescription = "info"
                )
                Text(
                    text = "Чтобы узнать наличие задолженности, выберите номер договора из списка ",
                    color = colorResource(id = R.color.black),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainMenuContent() {
    CreatingNew1Content(ClientInfoViewModel())
}