package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.api.ClientInfo

@Composable
fun AdditionalInfoDialog(isOpen: Boolean, onDismiss: () -> Unit, clientInfo: ClientInfo){
    if(isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Контактная информация")
                }
            },
            text = {
                Column {
                    CheckRow(R.drawable.ic_assignment, "Номер договора", clientInfo.agreementNumber)
                    CheckRow(R.drawable.ic_city, "Компания", clientInfo.name)
                    CheckRow(R.drawable.ic_place, "Адрес", clientInfo.addressUUTE)
                    CheckRow(R.drawable.ic_person, "Представитель", clientInfo.representativeName)
                    CheckRow(R.drawable.ic_phone, "Телефон", clientInfo.phoneNumber)
                    CheckRow(R.drawable.ic_email, "Почта", clientInfo.email)
                }
            },
            buttons = {}
        )
    }
}