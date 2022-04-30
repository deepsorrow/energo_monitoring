package com.example.energo_monitoring.compose.screens.mainMenu.checks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.data.api.ClientInfo
import kotlin.math.abs

@Composable
fun CheckItemInProgress(clientInfo: ClientInfo, onClick: (Int) -> Unit, progress: Int) {
    CheckItem(clientInfo = clientInfo, onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier.fillMaxWidth(), text = "Прогресс: $progress/7")
            Box(
                modifier = Modifier
                    .fillMaxWidth(((1/7) * progress).toFloat())
                    .height(20.dp)
                    .clip(RoundedCornerShape(125.dp))
                    .background(
                        Color.Red
                    )
            )
        }
    }
}

@Preview
@Composable
fun CheckItemInProgressPreview(){
    CheckItemInProgress(
        clientInfo = ClientInfo(
            "123456789",
            "ул. Пушкина дом Колотушкина",
            "АО Сбербанк России",
            "Василий Блаженов",
            "+7 902 759 14 40",
            "mainwisdom@gmail.com"
        ),
        onClick = {},
        progress = 6
    )
}