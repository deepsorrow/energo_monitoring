package com.example.feature_create_new_data.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.feature_create_new_data.data.ClientInfo

@Database(entities = [ClientInfo::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): DatabaseDAO

    companion object {
        private var myDatabase: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    "ResultData"
                ).build()
            }

            return myDatabase as MyDatabase
        }
    }
}