package com.example.energo_monitoring.compose.screens.syncScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.energo_monitoring.R

@Composable
fun SyncContentEmpty(){
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_hourglass_empty_24),
            contentDescription = "Пустые песочные часы",
            tint = Color.Gray
        )
        Text(text = "Завершенных проверок еще нет", color = Color.Gray, fontSize = 20.sp)
    }
}

@Preview
@Composable
fun PreviewSyncContentEmpty(){
    SyncContentEmpty()
}