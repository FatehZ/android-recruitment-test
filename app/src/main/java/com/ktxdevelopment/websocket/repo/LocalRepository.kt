package com.ktxdevelopment.websocket.repo


import com.ktxdevelopment.websocket.local.dao.InvestDao
import com.ktxdevelopment.websocket.model.local.InvestEntity
import com.ktxdevelopment.websocket.model.mappers.InvestMapper
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.remote.socket.WebSocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class LocalRepository @Inject constructor(private var dao: InvestDao, private var mapper: InvestMapper, private var socketService: WebSocketService) {

    suspend fun insertInvest(invest: InvestItem) = dao.insertInvest(mapper.mapToEntity(invest))

    suspend fun insertAllInvests(invests: List<InvestItem>) =
        dao.insertAllInvests(mapper.mapToEntity(invests))

    suspend fun saveInvests(invests: List<InvestItem>) = dao.refreshAll(mapper.mapToEntity(invests))

    fun getAllInvests(): Flow<List<InvestItem>> {
        return mapper.mapList(dao.getAllInvests(), InvestEntity::asDto) as Flow<List<InvestItem>>
    }

        suspend fun clearDb() = dao.clearDb()
    }
