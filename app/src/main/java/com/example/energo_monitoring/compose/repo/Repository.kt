package com.example.energo_monitoring.compose.repo

import android.content.Context
import com.example.energo_monitoring.compose.NewClientInfo
import com.example.energo_monitoring.compose.domain.db.DatabaseDAO
import com.example.energo_monitoring.compose.domain.db.MyDatabase

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

    val getAllClientInfos: List<NewClientInfo> = dao!!.getClientInfos()

    fun insertClientInfo(clientInfo: NewClientInfo) {
        dao!!.insertClientInfo(clientInfo = clientInfo)
    }
}