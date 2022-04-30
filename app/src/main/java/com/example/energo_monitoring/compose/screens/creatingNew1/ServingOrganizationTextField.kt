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
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel

@Entity
data class ServingOrganization(
    @PrimaryKey
    var id: Int,

    var name: String,
    var contactName: String,
    var contactPhone: String,
    var address: String = "",

    // Объект на обслуживании (?)
    var obj: String = "",
) : IListEntry {
    constructor() : this(0, "", "", "", "", "")

    override val listLabel: String
        get() = name
}

@Composable
fun ServingOrganizationTextField(
    placeholder: String,
    initialValue: ServingOrganization?,
    available: List<ServingOrganization>,
    onValueChanged: (ServingOrganization) -> Unit,
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
        value = value?.name ?: "",
        onValueChange = {}, // NO-OP
        // мы меняем значения внутри диалога
        enabled = false,
    )

    if (openDialog.value) {
        ChooseFromListDialog(
            title = "Выбрать обслуживающую организацию",
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

@Preview
@Composable
fun PreviewServingOrganizationTextField() {
    ServingOrganizationTextField(placeholder = "Обслуживающая организация", null, listOf()) {}
}
