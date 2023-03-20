package com.ktxdevelopment.websocket.model.connection


data class SocketConnectionState(
    val connected: Boolean = false,
    val error: Throwable? = null
)