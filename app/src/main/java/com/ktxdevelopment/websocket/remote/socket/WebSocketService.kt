package com.ktxdevelopment.websocket.remote.socket

import android.util.Log
import com.google.gson.Gson
import com.ktxdevelopment.websocket.model.remote.ResponseModel
import com.ktxdevelopment.websocket.util.Constants
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URI

import javax.inject.Singleton

@Singleton
class WebSocketService {

    private val TAG: String = "LTS_TAG"

    private val socket: Socket

    private val _socketState = MutableStateFlow(SocketState())
    val socketState = _socketState.asStateFlow()

    init {

        val uri: URI = URI.create("https://q.investaz.az:3000/")
        val options = IO.Options()
        options.transports = arrayOf("websocket")
        options.path = ("/socket.io")

        socket = IO.socket(uri, options)

        observeSocketConnection()
        //
        observeConnectionError()
        //
        observeSocketData()

    }

    private fun observeSocketConnection() {
        socket.on(Constants.Socket.CONNECT) {
            _socketState.value = _socketState.value.copy(connected = true)
        }.on(Constants.Socket.DISCONNECT) {
            _socketState.value = _socketState.value.copy(connected = false)
        }
    }


    private fun observeConnectionError() {
        socket.on(Constants.Socket.CONNECT_ERROR) { data ->
            if (data != null && data.isNotEmpty()) {
                val error = data[0]
                if (error != null && error is Throwable) {
                    Log.i(TAG, "collectConnectionError: ${data[0]}")
                    _socketState.value = _socketState.value.copy(error = error)
                }
            }
        }.on(Constants.Socket.ERROR) { data ->
            if (data != null && data.isNotEmpty()) {
                Log.i(TAG, "collectConnectionError: ${data[0]}")
                val error = data[0]
                if (error != null && error is Throwable) _socketState.value =
                    _socketState.value.copy(error = error)
            }
        }
    }

    private fun observeSocketData() {
        socket.on(Constants.Socket.MESSAGE) { data ->
            if (data.isNotEmpty()) {
                val jsonString: String = data[0].toString()
                val result = Gson().fromJson(jsonString, ResponseModel::class.java)
                _socketState.value = _socketState.value.copy(error = null, data = result)
            }
        }
    }

    fun connect() {
        socket.connect()
    }

    fun disconnect() {
        socket.disconnect()
    }
}