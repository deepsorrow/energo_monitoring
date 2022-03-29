package com.example.energo_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.compose.NewClientInfo
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.data.api.ClientInfo

@Composable
fun AgreementNumTextField(
    placeholder: String, viewModel: ClientInfoViewModel,
    onValueChanged: (String) -> Unit,
) {

    val openDialog = remember {
        mutableStateOf(false)
    }
    var value by remember {
        mutableStateOf(viewModel.agreementNumber.value.toString())
    }

    OutlinedTextField(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                openDialog.value = true
            }),
        label = {
            Text(text = placeholder)
        },
        value = if (value == "0") "" else value,
        onValueChange = onValueChanged,
        enabled = false,
    )
    if (openDialog.value) {
        ChooseFromListDialog(
            title = "Выбрать номер договора",
            list = listOf(
                NewClientInfo(0, 123120321, "Школа №13", "", "", "", "", false),
                NewClientInfo(1, 523131321, "Дет. сад №5", "", "", "", "", false),
                NewClientInfo(2, 865120321, "Школа №153", "", "", "", "", false)
            ),
            openDialog = true,
            onCloseClicked = { openDialog.value = false },
            onConfirmClicked = {
                openDialog.value = false
                value = it.toString()
            }
        )
    }
}

@Composable
@Preview
fun PreviewAgreementNumTextField() {
    AgreementNumTextField(placeholder = "Номер договора абонента", ClientInfoViewModel(), {})
}