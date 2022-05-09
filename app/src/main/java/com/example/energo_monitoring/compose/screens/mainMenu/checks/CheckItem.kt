package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.data.SyncStatus
import com.example.energo_monitoring.checks.data.api.ClientInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckItem(
    clientInfo: ClientInfo,
    onClick: () -> Unit,
    onInfoClicked: () -> Unit,
    onSyncClicked: () -> Unit,
    progress: Int,
    syncStatus: SyncStatus
) {
    val progressText = when (progress) {
        0 -> "Нажмите, чтобы начать..."
        1 -> "Введите фото проекта..."
        2 -> "Проследуйте на объект..."
        3 -> "Проверка основных данных..."
        4 -> "Проверка приборов и датчиков..."
        5 -> "Проверка длин участков..."
        6 -> "Ввод настроечных параметров..."
        7 -> "Фото вида узла, приборный парк..."
        else -> ""
    }

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color.White,
            Color(216, 201, 247, 84),
            Color.White
        ),
        tileMode = TileMode.Repeated
    )
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = onClick,
        elevation = 5.dp,
        shape = RoundedCornerShape(6)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
        ) {
            Row {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    when (syncStatus) {
                        SyncStatus.SYNCED -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_check_circle),
                                contentDescription = "Sync"
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Синхронизировано",
                                color = Color.DarkGray
                            )
                        }
                        SyncStatus.NOT_SYNCED -> {
                            OutlinedButton(
                                onClick = onSyncClicked
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_sync_circle),
                                    contentDescription = "Sync"
                                )
                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = "Синхронизировать",
                                    color = Color.DarkGray
                                )
                            }
                        }
                        SyncStatus.NOT_LOADED -> {
                            OutlinedButton(
                                onClick = onSyncClicked
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_sync_circle),
                                    contentDescription = "Sync"
                                )
                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = "Выгрузить",
                                    color = Color.DarkGray
                                )
                            }
                        }
                        else -> {}
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .width(50.dp)
                            .defaultMinSize(minHeight = 40.dp),
                        contentPadding = PaddingValues(5.dp),
                        onClick = onInfoClicked
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_info_24),
                            contentDescription = "Contact info"
                        )
                    }
                }
            }
            CheckRow(
                icon = R.drawable.ic_city,
                description = "Организация",
                text = clientInfo.name
            )
            CheckRow(
                icon = R.drawable.ic_place,
                description = "Адрес",
                text = clientInfo.addressUUTE
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                text = progressText,
                color = Color.DarkGray
            )
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = progress.toFloat() / 7
            )
        }

    }
}

@Composable
fun CheckRow(@DrawableRes icon: Int, description: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = 5.dp),
            painter = painterResource(id = icon),
            contentDescription = description
        )
        Text(
            text = text
        )
    }
}

@Preview
@Composable
fun CheckItemPreview() {
    CheckItem(
        clientInfo = ClientInfo(
            "123456789",
            "ул. Пушкина дом Колотушкина",
            "АО Сбербанк России",
            "Василий Блаженов",
            "+7 902 759 14 40",
            "mainwisdom@gmail.com"
        ),
        onClick = {},
        progress = 5,
        onInfoClicked = {},
        onSyncClicked = {},
        syncStatus = SyncStatus.NOT_LOADED
    )
}