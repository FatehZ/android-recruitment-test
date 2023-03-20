package com.ktxdevelopment.websocket.util

object Constants {

    const val DB_NAME = "message_db"

    const val INVEST_TABLE = "invest_table"


    object Socket {
        const val CONNECT = "connect"
        const val DISCONNECT = "disconnect"

        const val CONNECT_ERROR = "connect_error"
        const val ERROR = "error"
        const val MESSAGE = "message"
    }


    object Trend {
        const val UP = "up"
        const val DOWN = "down"
    }
}