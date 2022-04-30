package com.example.energo_monitoring.compose.domain.db

import androidx.room.Dao

@Dao
abstract class CreatingNewDAO {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract fun insertClientInfo(clientInfo: NewClientInfo)
//    @Query("SELECT * FROM ClientInfo")
//    abstract fun getClientInfos(): List<NewClientInfo>
//    @Query("SELECT * FROM ClientInfo WHERE agreementNumber Like :query")
//    abstract fun searchClientInfoByNumber(query: String): NewClientInfo
}