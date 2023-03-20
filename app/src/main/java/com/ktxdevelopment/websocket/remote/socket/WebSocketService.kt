package com.ktxdevelopment.websocket.remote.socket

import android.util.Log
import com.google.gson.Gson
import com.ktxdevelopment.websocket.model.remote.ResponseModel
import com.ktxdevelopment.websocket.util.Constants
import com.ktxdevelopment.websocket.util.update
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URI


class WebSocketService {

    private val socket: Socket

    private val _socketState = MutableStateFlow(SocketState())
    val socketState = _socketState.asStateFlow()

    init {
        val uri: URI = URI.create("https://q.investaz.az")
        val options = IO.Options.builder()
            .setPath("/live")
            .build()
        socket = IO.socket(uri, options)

        collectSocketConnected()
        collectSocketDisconnected()
        //
        collectConnectionError()
        collectSocketError()
        //
        collectSocketData()
    }

    private fun collectSocketConnected() {
        socket.on(Constants.Socket.CONNECT) {
            Log.i(TAG, "SOCKET CONNECTED")
            _socketState.update { it.copy(connected = true) }
        }
    }

    val TAG  = "LTS_TAG"

    private fun collectSocketDisconnected() {
        socket.on(Constants.Socket.DISCONNECT) {
            Log.i(TAG, "SOCKET DICCONNECTED")
            _socketState.update { it.copy(connected = false) }
        }
    }

    private fun collectConnectionError() {
        socket.on(Constants.Socket.CONNECT_ERROR) { data ->
            if (data != null && data.isNotEmpty()) {
                val error = data[0]
                if (error!= null && error is Throwable) _socketState.value = _socketState.value.copy(error = error)
            }
        }
    }

    private fun collectSocketError() {
        socket.on(Constants.Socket.ERROR) { data ->
            if (data != null && data.isNotEmpty()) {
                val error = data[0]
                if (error!= null && error is Throwable) _socketState.update { it.copy(error = error) }
            }
        }
    }

    private fun collectSocketData() {
        socket.on(Constants.Socket.MESSAGE) { data ->
            if (data.isNotEmpty()) {
                val jsonString: String = data[0].toString()
                val result = Gson().fromJson(jsonString, ResponseModel::class.java)
                _socketState.update { it.copy(data = result) }
            }
        }

    }
        fun connect() { socket.connect() }

        fun disconnect() { socket.disconnect() }
}