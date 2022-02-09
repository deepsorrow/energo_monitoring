package com.example.feature_create_new_data.presentation.screens.creatingNew1

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_create_new_data.data.ClientInfo
import com.example.feature_create_new_data.presentation.viewmodels.SharedViewModel

@Composable
fun MainMenuContent(viewModel: SharedViewModel) {
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
        val clientInfo = viewModel.clientInfo.value
        CreatingTextField(placeholder = "Номер договора абонента", clientInfo.agreementNumber.toString()) {
            clientInfo.agreementNumber = it.toInt()
        }
        CreatingTextField(placeholder = "Название контрагента", clientInfo.name.toString()) {
            clientInfo.name = it
        }
        CreatingTextField(placeholder = "Адрес УУТЭ контрагента", clientInfo.addressUUTE.toString()) {
            clientInfo.addressUUTE = it
        }
        CreatingTextField(placeholder = "Представитель абонента", clientInfo.representativeName.toString()) {
            clientInfo.representativeName = it
        }
        CreatingTextField(placeholder = "Телефон представителя абонента", clientInfo.phoneNumber.toString()) {
            clientInfo.phoneNumber = it
        }
        CreatingTextField(placeholder = "Электронный адрес абонента", clientInfo.email.toString()) {
            clientInfo.email = it
        }
        CreatingTextField(placeholder = "Обслуживающая организация", clientInfo.email.toString()) {

        }
        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ){
            Text(
                text = "Задолженность: ",
                fontSize = 16.sp
            )
            Text(
                text = "<не выбран номер договора...>",
                fontSize = 16.sp
            )
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Продолжить")
        }
    }
}

@Preview
@Composable
fun PreviewMainMenuContent() {
    MainMenuContent(SharedViewModel())
}