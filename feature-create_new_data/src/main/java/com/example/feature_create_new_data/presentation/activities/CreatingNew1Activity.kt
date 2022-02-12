package com.example.feature_create_new_data.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import com.example.feature_create_new_data.data.DrawerScreens
import com.example.feature_create_new_data.presentation.screens.creatingNew1.SetupNavigation
import com.example.feature_create_new_data.presentation.viewmodels.ChecksViewModel
import com.example.feature_create_new_data.presentation.viewmodels.ClientInfoViewModel
import com.example.feature_create_new_data.presentation.viewmodels.SharedViewModel
import com.example.feature_create_new_data.presentation.viewmodels.SyncViewModel

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