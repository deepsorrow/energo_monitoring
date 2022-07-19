package com.example.energy_monitoring.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExitConfirmAlertDialog(showAlertDialog: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Внимание",
                textAlign = TextAlign.Center
            ) },
            text = { Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Выйти без сохранения?",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            ) } ,
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
                        Text(text = "Да")
                    }
                }
            }
        )
    }
}