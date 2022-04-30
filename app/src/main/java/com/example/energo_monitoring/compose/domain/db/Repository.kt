package com.example.energo_monitoring.compose.domain.db

import android.content.Context

class Repository {
    companion object {
        private var repository: Repository? = null
        private var dao: CreatingNewDAO? = null

        fun getRepository(context: Context): Repository {
            if (repository == null) {
                dao = CreatingNewDatabase.getDatabase(context).dao()
                repository = Repository()
            }

            return repository as Repository
        }
    }

//    val getAllClientInfos: List<NewClientInfo> = dao!!.getClientInfos()
//
//    fun insertClientInfo(clientInfo: NewClientInfo) {
//        dao!!.insertClientInfo(clientInfo = clientInfo)
//    }
}