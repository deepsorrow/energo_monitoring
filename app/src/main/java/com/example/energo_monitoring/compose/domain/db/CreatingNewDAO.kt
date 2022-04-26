package com.example.energo_monitoring.compose.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.energo_monitoring.compose.NewClientInfo

@Dao
abstract class CreatingNewDAO {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract fun insertClientInfo(clientInfo: NewClientInfo)
//    @Query("SELECT * FROM ClientInfo")
//    abstract fun getClientInfos(): List<NewClientInfo>
//    @Query("SELECT * FROM ClientInfo WHERE agreementNumber Like :query")
//    abstract fun searchClientInfoByNumber(query: String): NewClientInfo
}