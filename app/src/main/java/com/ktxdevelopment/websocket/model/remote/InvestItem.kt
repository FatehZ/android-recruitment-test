package com.ktxdevelopment.websocket.model.remote

import com.google.gson.annotations.SerializedName
import com.ktxdevelopment.websocket.model.dto.Domain
import com.ktxdevelopment.websocket.model.dto.Dto
import com.ktxdevelopment.websocket.model.local.InvestEntity


data class InvestItem(
    @SerializedName("0") var trend: String = "",
    @SerializedName("1") var company: String = "",
    @SerializedName("2") var val1: String = "",
    @SerializedName("3") var val2: String = "",
    @SerializedName("4") var val3: String = "",
    @SerializedName("5") var val4: String = "",
    @SerializedName("6") var rank: Int,
    @SerializedName("7") var date: String = ""
) : Dto {

    override fun asDomain(): InvestEntity = InvestEntity(
        trend,
        company,
        val1,
        val2,
        val3,
        val4,
        rank,
        date
    )
}