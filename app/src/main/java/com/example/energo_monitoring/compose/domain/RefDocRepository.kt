package com.example.energo_monitoring.compose.domain

import com.example.energo_monitoring.compose.data.api.RefDoc
import com.example.energo_monitoring.compose.data.api.RefDocDao
import javax.inject.Inject

class RefDocRepository @Inject constructor(val dao: RefDocDao) {
    fun insertRefDoc(file: RefDoc) {
        dao.insertRefDoc(file)
    }

    fun getRefDoc(file: RefDoc) {
        dao.getRefDoc(file.id)
    }

    fun deleteRefDoc(file: RefDoc) {
        dao.deleteRefDoc(file.id)
    }
}