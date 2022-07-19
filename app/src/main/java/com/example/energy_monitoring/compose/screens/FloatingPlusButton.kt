package com.example.energy_monitoring.compose.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.energy_monitoring.R

@Composable
fun FloatingPlusButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        backgroundColor = Color(238, 238, 238, 255),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "Добавить файл",
            tint = Color.DarkGray
        )
    }
}