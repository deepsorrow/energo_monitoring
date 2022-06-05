package com.example.energy_monitoring.compose.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.energy_monitoring.compose.data.api.RefDoc
import com.example.energy_monitoring.compose.data.api.RefDocDao

@Database(entities = [RefDoc::class], version = 5)
abstract class RefDocDatabase : RoomDatabase() {
    abstract fun dao(): RefDocDao

    companion object {
        private var database: RefDocDatabase? = null

        fun getDatabase(context: Context?): RefDocDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context!!,
                    RefDocDatabase::class.java,
                    "RefDocDatabase"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }

            return database!!
        }
    }
}