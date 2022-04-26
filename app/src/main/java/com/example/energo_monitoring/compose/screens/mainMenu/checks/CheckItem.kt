package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
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
import com.example.energo_monitoring.R
import com.example.energo_monitoring.data.api.ClientInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckItem(clientInfo: ClientInfo, onClick: (Int) -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color.White,
            Color(180, 185, 221, 255)
        ),
        tileMode = TileMode.Repeated
    )
    Card(
        modifier = Modifier
            .background(gradient)
            .height(200.dp)
            .padding(start = 5.dp, end = 5.dp, top = 5.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = { onClick(clientInfo.dataId) }
    ) {
        Row {
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, end = 5.dp),
                    textAlign = TextAlign.End,
                    text = clientInfo.agreementNumber
                )
                CheckRow(R.drawable.ic_city, "Компания", clientInfo.name)
                CheckRow(R.drawable.ic_place, "Адрес", clientInfo.addressUUTE)
                CheckRow(R.drawable.ic_person, "Представитель", clientInfo.representativeName)
                CheckRow(R.drawable.ic_phone, "Телефон", clientInfo.phoneNumber)
                CheckRow(R.drawable.ic_email, "Почта", clientInfo.email)
            }
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
        Text(text = text)
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
        onClick = {}
    )
}