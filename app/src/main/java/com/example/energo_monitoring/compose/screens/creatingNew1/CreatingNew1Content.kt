package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.R

@Composable
fun CreatingNew1Content(viewModel: ClientInfoViewModel, goToNextScreen: () -> Unit) {
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

        AgreementNumTextField(
            placeholder = "Номер договора абонента",
            viewModel.agreementNumber.value,
            viewModel.availableClientInfos
        ) {
            viewModel.agreementNumber.value = it

            if (
                !viewModel.modifiedByUserOnce ||
                viewModel.name.value == "" &&
                viewModel.addressUUTE.value == "" &&
                viewModel.representativeName.value == "" &&
                viewModel.phoneNumber.value == "" &&
                viewModel.email.value == ""
            ) {
                // Ничего не было введено пользователем
                // выставляем всё автоматически
                viewModel.name.value = it.name
                viewModel.addressUUTE.value = it.addressUUTE
                viewModel.representativeName.value = it.representativeName
                viewModel.phoneNumber.value = it.phoneNumber
                viewModel.email.value = it.email

                viewModel.modifiedByUserOnce = false
            }
        }

        CreatingTextField(placeholder = "Название контрагента", viewModel.name.value) {
            viewModel.name.value = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Адрес УУТЭ контрагента", viewModel.addressUUTE.value) {
            viewModel.addressUUTE.value = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Представитель абонента", viewModel.representativeName.value) {
            viewModel.representativeName.value = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Телефон представителя абонента", viewModel.phoneNumber.value) {
            viewModel.phoneNumber.value = it
            viewModel.modifiedByUserOnce = true
        }

        CreatingTextField(placeholder = "Электронный адрес абонента", viewModel.email.value) {
            viewModel.email.value = it
            viewModel.modifiedByUserOnce = true
        }

        ServingOrganizationTextField(
            placeholder = "Обслуживающая организация",
            viewModel.servingOrganization.value,
            viewModel.availableServingOrganizations
        ) {
            viewModel.servingOrganization.value = it
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

        Button(
            onClick = goToNextScreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Продолжить")
        }
    }
}

@Preview
@Composable
fun PreviewMainMenuContent() {
    CreatingNew1Content(ClientInfoViewModel(), {})
}