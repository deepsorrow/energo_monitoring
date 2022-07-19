package com.example.energy_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energy_monitoring.R
import com.example.energy_monitoring.compose.ContractInfo
import com.example.energy_monitoring.compose.screens.ExitConfirmAlertDialog

@Composable
fun CreatingNew1Content(
    viewModel: ClientInfoViewModel
) {

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
            placeholder = "Номер договора",
            viewModel.agreementNumber,
            viewModel.availableClientsInfo,
            onAgreementNumberChanged = { viewModel.agreementNumber = it }
        ) {
//            if (!viewModel.modifiedByUserOnce ||
//                viewModel.name == "" &&
//                viewModel.addressUUTE == "" &&
//                viewModel.representativeName == "" &&
//                viewModel.phoneNumber == "" &&
//                viewModel.email == ""
//            ) {
//                // Ничего не было введено пользователем
//                // выставляем всё автоматически
                viewModel.agreementNumber = it.agreementNumber
                viewModel.name = it.name
                viewModel.addressUUTE = it.addressUUTE
                viewModel.representativeName = it.representativeName
                viewModel.phoneNumber = it.phoneNumber
                viewModel.email = it.email
//            }
        }

        CreatingTextField(placeholder = "Название контрагента", viewModel.name) {
            viewModel.name = it
        }

        CreatingTextField(placeholder = "Адрес УУТЭ контрагента", viewModel.addressUUTE) {
            viewModel.addressUUTE = it
        }

        CreatingTextField(placeholder = "Представитель абонента", viewModel.representativeName) {
            viewModel.representativeName = it
        }

        CreatingTextField(placeholder = "Телефон представителя абонента", viewModel.phoneNumber) {
            viewModel.phoneNumber = it
        }

        CreatingTextField(placeholder = "Электронный адрес абонента", viewModel.email) {
            viewModel.email = it
        }

        ServingOrganizationTextField(
            placeholder = "Обслуживающая организация",
            viewModel.servingOrganization,
            viewModel.availableServingOrganizations
        ) {
            viewModel.servingOrganization = ServingOrganization()
            viewModel.servingOrganization = it
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
    //CreatingNew1Content(ClientInfoViewModel())
}