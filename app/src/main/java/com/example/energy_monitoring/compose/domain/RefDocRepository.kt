package com.example.energy_monitoring.compose.domain

import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.data.api.RefDocDao
import javax.inject.Inject

class RefDocRepository @Inject constructor(private val dao: RefDocDao) {
    fun insertRefDoc(file: RefDoc) {
        dao.insertRefDoc(file)
    }

    fun getRefDoc(id: Int): RefDoc? {
        return dao.getRefDoc(id)
    }

    fun getParentDoc(parentId: Int): RefDoc? {
        return dao.getParent(parentId)
    }

    fun deleteRefDoc(file: RefDoc) {
        dao.deleteRefDoc(file.id)
    }

    fun deleteRefDocs() {
        dao.deleteRefDocs()
    }

    fun getAllRefDocs(): List<RefDoc>? {
        return dao.getAllRefDocs()
    }
}