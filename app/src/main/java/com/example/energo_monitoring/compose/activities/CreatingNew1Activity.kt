package com.example.energo_monitoring.compose.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import com.example.energo_monitoring.compose.SetupNavigation
import com.example.energo_monitoring.compose.viewmodels.ChecksViewModel
import com.example.energo_monitoring.compose.viewmodels.ClientInfoViewModel
import com.example.energo_monitoring.compose.viewmodels.SharedViewModel
import com.example.energo_monitoring.compose.viewmodels.SyncViewModel

class CreatingNew1Activity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private val clientInfoViewModel: ClientInfoViewModel by viewModels()
    private val syncViewModel: SyncViewModel by viewModels()
    private val checksViewModel: ChecksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme{
                SetupNavigation(
                    syncViewModel = syncViewModel,
                    clientInfoViewModel = clientInfoViewModel,
                    sharedViewModel = sharedViewModel,
                    checksViewModel = checksViewModel
                )
            }
        }
    }
}