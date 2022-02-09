package com.example.feature_create_new_data.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.example.feature_create_new_data.presentation.screens.creatingNew1.MainMenu
import com.example.feature_create_new_data.presentation.viewmodels.SharedViewModel

class CreatingNew1Activity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme{
                MainMenu(
                    title = "Энергомониторинг",
                    viewModel = sharedViewModel
                )
            }
        }
    }
}