package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.R
import com.example.energo_monitoring.data.api.ClientInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckItem(
    clientInfo: ClientInfo,
    onClick: (Int) -> Unit,
    onInfoClicked: () -> Unit,
    progress: Int
) {
    val progressText = when(progress){
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
            Color(235, 236, 241, 255),
            Color.White
        ),
        tileMode = TileMode.Repeated
    )
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = { onClick(clientInfo.dataId) }
    ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient)
            ) {
                Row {
                    Box {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            textAlign = TextAlign.Center,
                            text = clientInfo.agreementNumber,
                            fontSize = 16.sp
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End) {
                            OutlinedButton(
                                modifier = Modifier.padding(end = 5.dp),
                                onClick = onInfoClicked
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_baseline_info_24),
                                    contentDescription = "Contact info"
                                )
                            }
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
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                    text = progressText,
                    color = Color.DarkGray
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.toFloat() / 7)
                        .height(5.dp)
                        .background(Color(253, 216, 53, 255))
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
        progress = 0,
        onInfoClicked = {}
    )
}