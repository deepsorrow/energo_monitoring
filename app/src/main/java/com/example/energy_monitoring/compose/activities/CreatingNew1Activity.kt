package com.example.energy_monitoring.compose.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import com.example.energy_monitoring.compose.SetupNavigation
import com.example.energy_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energy_monitoring.compose.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatingNew1Activity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                SetupNavigation(
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val route = intent?.getStringExtra("route")
        if (route != null) {
            sharedViewModel.changeDestination(route) {  }
        }
    }
}