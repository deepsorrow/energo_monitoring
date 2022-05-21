package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.data.SyncStatus
import com.example.energo_monitoring.checks.data.api.ClientInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckItem(
    clientInfo: ClientInfo,
    onClick: () -> Unit,
    onActionClick: (selectedAction: DropDownClientActions, clientInfo: ClientInfo) -> Unit,
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
            Color(216, 201, 247, 37),
            Color.White
        ),
        tileMode = TileMode.Repeated
    )

    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

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
                Box {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(26.dp),
                            text = clientInfo.name + "\n",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(21.dp),
                            text = clientInfo.addressUUTE + "\n",
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            fontSize = 15.sp
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Box {
                            IconButton(onClick = {
                                isDropdownMenuExpanded = !isDropdownMenuExpanded
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                    tint = Color(158, 158, 158, 255),
                                    contentDescription = "Меню опций"
                                )
                            }
                                DropdownMenu(
                                    expanded = isDropdownMenuExpanded,
                                    onDismissRequest = {
                                        isDropdownMenuExpanded = !isDropdownMenuExpanded
                                    },
                                ) {
                                    DropDownClientActions.values().forEach { action ->
                                        DropdownMenuItem(
                                            modifier = Modifier.width(220.dp),
                                            onClick = { onActionClick(action, clientInfo) }
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    painter = painterResource(id = action.icon),
                                                    tint = Color.DarkGray,
                                                    contentDescription = action.title
                                                )
                                                Text(
                                                    modifier = Modifier.padding(start = 5.dp),
                                                    text = action.title
                                                )
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .height(1.dp)
                )

                CheckRow(
                    icon = R.drawable.ic_person,
                    description = "Контактное лицо",
                    text = "${clientInfo.representativeName}, ${clientInfo.phoneNumber}"
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .height(1.dp)
                )

                Row(modifier = Modifier.padding(start = 10.dp)) {
                    when (syncStatus) {
                        SyncStatus.SYNCED -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_check),
                                contentDescription = "Sync",
                                colorFilter = ColorFilter.tint(Color(56, 142, 60)),
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Версия актуальная",
                                color = Color.DarkGray
                            )
                        }
                        SyncStatus.NOT_SYNCED -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_sync),
                                contentDescription = "Sync",
                                colorFilter = ColorFilter.tint(Color(249, 214, 37, 255)),
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Не сохранено на сервере",
                                color = Color.DarkGray
                            )
                        }
                        SyncStatus.NOT_LOADED -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_sync),
                                colorFilter = ColorFilter.tint(Color(249, 214, 37, 255)),
                                contentDescription = "Sync"
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Необходимо загрузить с сервера",
                                color = Color.DarkGray
                            )
                        }
                        else -> {}
                    }
                }
                Row(
                    modifier = Modifier.padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_storage),
                        tint = Color.LightGray,
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, top = 5.dp),
                        text = "Занимаемое место: 11 МБ",
                        color = Color.DarkGray
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 5.dp),
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
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
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
        onActionClick = { _: DropDownClientActions, _: ClientInfo -> },
        progress = 5,
        syncStatus = SyncStatus.NOT_LOADED
    )
}