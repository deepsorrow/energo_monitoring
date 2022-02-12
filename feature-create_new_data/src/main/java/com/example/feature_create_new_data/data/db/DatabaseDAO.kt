package com.example.feature_create_new_data.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feature_create_new_data.data.ClientInfo

@Dao
abstract class DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertClientInfo(clientInfo: ClientInfo)
    @Query("SELECT * FROM ClientInfo")
    abstract fun getClientInfos(): List<ClientInfo>
    @Query("SELECT * FROM ClientInfo WHERE agreementNumber Like :query")
    abstract fun searchClientInfoByNumber(query: String): ClientInfo
}