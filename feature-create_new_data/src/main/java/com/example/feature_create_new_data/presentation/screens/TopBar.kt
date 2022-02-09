package com.example.feature_create_new_data.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature_create_new_data.R

@Composable
fun TopBar(text: String){
    TopAppBar(
        title = {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Лого"
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = text,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    tint = MaterialTheme.colors.onPrimary,
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@Preview
@Composable
fun PreviewTopBar(){
    TopBar("Энергомониторинг")
}