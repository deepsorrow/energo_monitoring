package com.example.feature_create_new_data.presentation.screens.creatingNew1

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.feature_create_new_data.presentation.screens.TopBar
import com.example.feature_create_new_data.presentation.viewmodels.SharedViewModel

@Composable
fun MainMenu(title: String, viewModel: SharedViewModel){
    Scaffold(
        topBar = { TopBar(title) },
        content = { MainMenuContent(viewModel) }
    )
}

@Composable
@Preview
private fun MainMenuPreview(){
    MainMenu("Энергомониторинг", SharedViewModel())
}