package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = "Акт ввода в коммерческий учёт",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        AgreementNumTextField(placeholder = "Номер договора абонента", viewModel) {
            viewModel.agreementNumber.value = it.toInt()
        }
        CreatingTextField(placeholder = "Название контрагента", viewModel.name.value) {
            viewModel.name.value = it
        }
        CreatingTextField(placeholder = "Адрес УУТЭ контрагента", viewModel.addressUUTE.value) {
            viewModel.addressUUTE.value = it
        }
        CreatingTextField(placeholder = "Представитель абонента",
            viewModel.representativeName.value
        ) {
            viewModel.representativeName.value = it
        }
        CreatingTextField(placeholder = "Телефон представителя абонента",
            viewModel.phoneNumber.value
        ) {
            viewModel.phoneNumber.value = it
        }
        CreatingTextField(placeholder = "Электронный адрес абонента", viewModel.email.value) {
            viewModel.email.value = it
        }
        CreatingTextField(placeholder = "Обслуживающая организация", viewModel.email.value) {

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
            onClick = { /*TODO*/ },
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
    CreatingNew1Content(ClientInfoViewModel())
}