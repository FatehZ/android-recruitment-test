package com.ktxdevelopment.websocket.remote.net

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {

    fun observe(): Flow<Status>

    enum class Status {
       Unavailable,
        Available,
        Lost
    }
}