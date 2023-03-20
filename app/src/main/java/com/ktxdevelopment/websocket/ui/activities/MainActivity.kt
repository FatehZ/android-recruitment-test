package com.ktxdevelopment.websocket.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ktxdevelopment.websocket.R
import com.ktxdevelopment.websocket.databinding.ActivityMainBinding
import com.ktxdevelopment.websocket.ui.fragments.FragmentMain
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val fragmentMain = FragmentMain()
        supportFragmentManager.beginTransaction().add(R.id.navHost, fragmentMain).commit()
    }



//        socket.on("message") { data ->
//            try {
//                if (data.isNotEmpty()) {
//                    val json = data[0].toString()
//                    Log.i("LTS_TAG", "initSocketConnection: $json")
//                }else{
//                    Log.i("LTS_TAG", "initSocketConnection: empty")
//                }
//            }catch (e:Exception){
//                Log.i("LTS_TAG", "initSocketConnection: $e")
//            }
//        }
//        socket.emit("message", object : AckWithTimeout(3000) {
//            override fun onTimeout() {
//                Log.i("LTS_TAG","time_out")
//            }
//
//            override fun onSuccess(vararg args: Any) {
//                Log.i("LTS_TAG","success")
//            }
//        })




//        socket.connect()
//        socket.on(Socket.EVENT_CONNECT) {
//            Log.i("LTS_TAG", "initSocketConnection: ${it[0]}")
//        }
//
//        socket.on(Socket.EVENT_DISCONNECT) {
//            Log.i("LTS_TAG", "finishSocketConnection: ${it[0]}")
//        }
//        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
//            val error = args[0]
//        }
//
    }







//    private fun collectUiState() = with(binding) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.uiState.collectLatest { uiState ->
//                stockAdapter.submitList(uiState.stocks)
//
//                connectionStatusIconImageView.apply {
//                    if (uiState.isConnected) {
//                        setImageResource(R.drawable.ic_active)
//                        setColor(android.R.color.holo_green_dark)
//                    } else {
//                        setImageResource(R.drawable.ic_not_active)
//                        setColor(android.R.color.holo_red_dark)
//                    }
//                }
//
//                connectionStatusTextView.text = if (uiState.isConnected) {
//                    getString(R.string.connection_status_active)
//                } else {
//                    getString(R.string.connection_status_not_active)
//                }
//
//                connectButton.isEnabled = !uiState.isConnected
//                disconnectButton.isEnabled = uiState.isConnected
//            }
//        }
//    }
