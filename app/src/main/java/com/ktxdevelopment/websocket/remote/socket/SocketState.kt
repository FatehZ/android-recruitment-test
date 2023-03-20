package com.ktxdevelopment.websocket.remote.socket

import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.model.remote.ResponseModel

data class SocketState (
    var error: Throwable? = null,
    var connected: Boolean = false,
    var data: ResponseModel = ResponseModel()
)