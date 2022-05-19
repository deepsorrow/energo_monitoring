package com.example.energo_monitoring.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.energo_monitoring.R

@Composable
fun TopBar(title: String, onMoreButtonClicked: (() -> Unit)? = null, onNavigationIconClicked: () -> Unit){
    TopAppBar(
        title = {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Лого"
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClicked) {
                Icon(
                    tint = MaterialTheme.colors.onPrimary,
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            if (onMoreButtonClicked != null) {
                IconButton(onClick = onMoreButtonClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = "Действия"
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@Preview
@Composable
fun PreviewTopBar(){
    TopBar("Энергомониторинг", onNavigationIconClicked = {}, onMoreButtonClicked = {})
}