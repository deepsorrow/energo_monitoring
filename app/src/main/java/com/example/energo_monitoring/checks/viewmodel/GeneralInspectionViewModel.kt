package com.example.energo_monitoring.checks.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import javax.inject.Inject

class GeneralInspectionViewModel @Inject constructor()
    : ViewModel() {

    fun getProjectInfo(){
        //ResultDataDatabase.getDatabase(context).resultDataDAO().getProjectDescription(0)
    }
}