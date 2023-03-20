package com.ktxdevelopment.websocket.model.dto

interface Dto {
    fun asDomain() : Domain
}

interface Domain {
    fun asDto() : Dto
}