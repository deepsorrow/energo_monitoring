package com.example.energy_monitoring.compose.screens.referenceInfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddNewFolderDialog(
    value: String,
    onValueChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = { Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Введите имя новой папки",
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        ) },
        text = {
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = { onValueChanged(it) }
                )
            }
        },
        onDismissRequest = onDismiss,
        buttons = {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    modifier = Modifier.width(130.dp),
                    onClick = onDismiss
                ) {
                    Text(text = "Отмена")
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    modifier = Modifier.width(130.dp),
                    onClick = onConfirm
                ) {
                    Text(text = "Создать")
                }
            }
        }
    )
}