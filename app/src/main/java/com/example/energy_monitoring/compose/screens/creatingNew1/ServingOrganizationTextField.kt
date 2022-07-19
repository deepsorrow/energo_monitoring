package com.example.energy_monitoring.compose.screens.creatingNew1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.energy_monitoring.R

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
    initialValue: ServingOrganization,
    available: List<ServingOrganization>,
    onValueChanged: (ServingOrganization) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(initialValue) }

    Box(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = placeholder)
            },
            value = value.name,
            onValueChange = {
                value.name = it
                onValueChanged(value)
            },
            maxLines = 1 // временный костыль, чтобы кнопка влазила
        )
        Button(
            modifier = Modifier.height(56.dp).padding(top = 7.dp, end = 2.dp),
            onClick = { openDialog = true },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(248, 248, 248, 255)
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                "Выбрать договор",
                tint = Color.DarkGray
            )
        }
    }

    if (openDialog) {
        ChooseFromListDialog(
            title = "Выбор обслуживающей организации",
            list = available,
            openDialog = true,
            showSearch = true,
            onCloseClicked = { openDialog = false },
            onConfirmClicked = {
                openDialog = false
                value = it
                onValueChanged.invoke(it)
            }
        )
    }
}

@Preview
@Composable
fun PreviewServingOrganizationTextField() {
    //ServingOrganizationTextField(placeholder = "Обслуживающая организация", null, listOf()) {}
}
