package com.example.energy_monitoring.compose.screens.mainMenu.checks

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energy_monitoring.compose.data.SyncStatus

@Composable
fun ConfirmLocalDeleteDialog(
    showAlertDialog: Boolean,
    syncStatus: SyncStatus?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showAlertDialog && syncStatus != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                val title = when (syncStatus) {
                    SyncStatus.SYNCED, SyncStatus.NOT_LOADED, SyncStatus.PARTIALLY_SYNCED -> "Информация"
                    SyncStatus.NOT_SYNCED -> "Внимание"
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            },
            text = {
                val text = when (syncStatus) {
                    SyncStatus.SYNCED, SyncStatus.PARTIALLY_SYNCED -> "Все файлы сохранены на сервере," +
                            " в будущем для восстановления потребуется соединение с интернетом.\nПродолжить?"
                    SyncStatus.NOT_SYNCED -> "Файлы не сохранены на сервере!\nЧтобы не потерять данные," +
                            " стоит сначала синхронизировать, тогда файлы можно будет восстановить." +
                            "\nВсе равно продолжить?"
                    SyncStatus.NOT_LOADED -> "Проверка еще не была начата, файлов для удаления нет"
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    fontSize = 16.sp
                )
            },
            buttons = {
                if (syncStatus == SyncStatus.NOT_LOADED) {
                    OutlinedButton(
                        modifier = Modifier.width(120.dp),
                        onClick = onDismiss
                    ) {
                        Text(text = "Ок")
                    }
                } else {
                    Row(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
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
                            onClick = { onConfirm().also{ onDismiss() } }
                        ) {
                            Text(text = "Да")
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewAlertDialogRemoveLocally() {
    ConfirmLocalDeleteDialog(
        showAlertDialog = true,
        syncStatus = SyncStatus.NOT_SYNCED,
        onConfirm = { },
        onDismiss = { }
    )
}