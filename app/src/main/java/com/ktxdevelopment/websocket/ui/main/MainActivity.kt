package com.ktxdevelopment.websocket.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ktxdevelopment.websocket.databinding.ActivityMainBinding
import io.socket.client.AckWithTimeout
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeUI()
    }


    private fun initializeUI() {
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}