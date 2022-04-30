package com.example.energo_monitoring.compose.screens.creatingNew2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.energo_monitoring.compose.screens.TopBar
import kotlinx.coroutines.Job

@Composable
fun CreatingNew2Screen(openDrawer: () -> Job){
    Scaffold(
        topBar = {
            TopBar(
                title = "Заголовок",
                onNavigationIconClicked = { openDrawer() }
            )
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Привет мир!")
            }
        }
    )

}

@Preview
@Composable
fun CreatingNew2ScreenPreview(){
    CreatingNew2Screen { Job() }
}