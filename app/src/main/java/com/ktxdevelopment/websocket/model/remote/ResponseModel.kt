package com.ktxdevelopment.websocket.model.remote

data class ResponseModel(
    val result: List<InvestItem>? = arrayListOf(),
    val total: Int = 0
)