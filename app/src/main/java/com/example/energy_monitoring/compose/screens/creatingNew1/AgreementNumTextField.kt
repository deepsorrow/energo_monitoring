package com.example.energy_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energy_monitoring.compose.ContractInfo

@Composable
fun AgreementNumTextField(
    placeholder: String,
    initialValue: ContractInfo?,
    available: List<ContractInfo>,
    onValueChanged: (ContractInfo) -> Unit,
) {
    val openDialog = remember {
        mutableStateOf(false)
    }

    var value by remember {
        mutableStateOf(initialValue)
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
        value = value?.agreementNumber?.toString() ?: "",
        onValueChange = {},
        enabled = false,
    )

    if (openDialog.value) {
        ChooseFromListDialog(
            title = "Выбрать номер договора",
            list = available,
            openDialog = true,
            onCloseClicked = { openDialog.value = false },
            onConfirmClicked = {
                openDialog.value = false
                value = it
                onValueChanged.invoke(it)
            }
        )
    }
}

@Composable
@Preview
fun PreviewAgreementNumTextField() {
    AgreementNumTextField(placeholder = "Номер договора абонента", null, listOf()) {}
}
