@file:Suppress("UNCHECKED_CAST")

package com.ktxdevelopment.websocket.repo

import com.ktxdevelopment.websocket.local.dao.InvestDao
import com.ktxdevelopment.websocket.model.connection.SocketConnectionState
import com.ktxdevelopment.websocket.model.local.InvestEntity
import com.ktxdevelopment.websocket.model.mappers.InvestMapper
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.remote.socket.WebSocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteRepository @Inject constructor(private var socketService: WebSocketService, mapper: InvestMapper, dao: InvestDao) {

    val investModels: Flow<List<InvestItem>> = mapper.mapList(dao.getAllInvests(), InvestEntity::asDto) as Flow<List<InvestItem>>

    val connectionState: Flow<SocketConnectionState> = socketService.socketState.map { SocketConnectionState(connected = it.connected, error = it.error) }

    fun disconnect() = socketService.disconnect()
    fun connect() = socketService.connect()
}