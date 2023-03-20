package com.ktxdevelopment.websocket.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ktxdevelopment.websocket.model.dto.Domain
import com.ktxdevelopment.websocket.model.dto.Dto
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.util.Constants


@Entity(tableName = Constants.INVEST_TABLE)
data class InvestEntity (
    var trend : String = "",
    var company : String = "",
    var val1 : String = "",
    var val2 : String = "",
    var val3 : String = "",
    var val4 : String = "",
    var rank : Int,
    var date : String = ""
) : Domain {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    override fun asDto(): Dto = InvestItem(
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
