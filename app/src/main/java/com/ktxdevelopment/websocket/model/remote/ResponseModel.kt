package com.ktxdevelopment.websocket.model.remote

data class ResponseModel(
    val result: List<InvestItem>?,
    val total: Int = 0
)