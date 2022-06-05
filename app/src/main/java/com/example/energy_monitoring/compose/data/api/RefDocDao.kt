package com.example.energy_monitoring.compose.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class RefDocDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRefDoc(refDoc: RefDoc)

    @Query("DELETE FROM RefDoc WHERE id = :id")
    abstract fun deleteRefDoc(id: Int)

    @Query("SELECT * FROM RefDoc WHERE id = :id")
    abstract fun getRefDoc(id: Int): RefDoc?

    @Query("SELECT * FROM RefDoc WHERE id = :parentId")
    abstract fun getParent(parentId: Int): RefDoc?

    @Query("SELECT * FROM RefDoc")
    abstract fun getAllRefDocs(): List<RefDoc>?
}