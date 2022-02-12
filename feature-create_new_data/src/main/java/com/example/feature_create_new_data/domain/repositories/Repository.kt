package com.example.feature_create_new_data.domain.repositories

import android.content.Context
import com.example.feature_create_new_data.data.ClientInfo
import com.example.feature_create_new_data.data.db.DatabaseDAO
import com.example.feature_create_new_data.data.db.MyDatabase

class Repository {
    companion object {
        private var repository: Repository? = null
        private var dao: DatabaseDAO? = null

        fun getRepository(context: Context): Repository {
            if (repository == null) {
                dao = MyDatabase.getDatabase(context).dao()
                repository = Repository()
            }

            return repository as Repository
        }
    }

    val getAllClientInfos: List<ClientInfo> = dao!!.getClientInfos()

    fun insertClientInfo(clientInfo: ClientInfo) {
        dao!!.insertClientInfo(clientInfo = clientInfo)
    }
}