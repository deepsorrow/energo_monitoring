package com.example.energo_monitoring.compose.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.energo_monitoring.compose.NewClientInfo

@Database(entities = [NewClientInfo::class], version = 1)
abstract class CreatingNewDatabase : RoomDatabase() {

    abstract fun dao(): CreatingNewDAO

    companion object {
        private var creatingNewDatabase: CreatingNewDatabase? = null

        fun getDatabase(context: Context): CreatingNewDatabase {
            if (creatingNewDatabase == null) {
                creatingNewDatabase = Room.databaseBuilder(
                    context,
                    CreatingNewDatabase::class.java,
                    "ResultData"
                ).build()
            }

            return creatingNewDatabase as CreatingNewDatabase
        }
    }
}