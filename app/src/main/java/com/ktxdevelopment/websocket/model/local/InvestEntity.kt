package com.ktxdevelopment.websocket.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ktxdevelopment.websocket.model.remote.InvestItem


@Entity(tableName = "invest_table")
data class InvestEntity (
    var trend : String = "",
    var company : String = "",
    var price1 : String = "",
    var price2 : String = "",
    var price3 : String = "",
    var price4 : String = "",
    var rank : Int,
    var date : String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}