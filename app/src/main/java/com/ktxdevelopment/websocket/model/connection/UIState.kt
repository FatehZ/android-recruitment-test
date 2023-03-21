package com.ktxdevelopment.websocket.model.connection

import com.ktxdevelopment.websocket.model.remote.InvestItem

data class UIState(
    var data: List<InvestItem> = arrayListOf(),
    var state: UIConnectionState = UIConnectionState.OFFLINE
)