@file:Suppress("UNCHECKED_CAST")

package com.ktxdevelopment.websocket.repo

import com.ktxdevelopment.websocket.model.connection.SocketConnectionState
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.remote.socket.WebSocketService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RemoteRepository @Inject constructor(private var socketService: WebSocketService) {

    val investModels: Flow<List<InvestItem>> = socketService.socketState.mapLatest { it.data.result }

    val connectionState: Flow<SocketConnectionState> = socketService.socketState.mapLatest { SocketConnectionState(connected = it.connected, error = it.error) }

    fun disconnect() = socketService.disconnect()
    fun connect() = socketService.connect()
}