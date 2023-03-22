package com.ktxdevelopment.websocket.mvvm

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.postDelayed
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ktxdevelopment.websocket.flow.local.ObserveLocalDataUsecase
import com.ktxdevelopment.websocket.flow.socket.*
import com.ktxdevelopment.websocket.model.connection.UIConnectionState.*
import com.ktxdevelopment.websocket.model.connection.UIState
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.remote.net.NetworkManager
import com.ktxdevelopment.websocket.remote.net.NetworkObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private var connectionObserver: ObserveConnectionUsecase,
    private var localDataObserver: ObserveLocalDataUsecase,
    private var dataObserver: ObserveDataUsecase,
    private var saveDataUsecase: SaveDataUsecase,
    private var connect: ConnectUsecase,
    private var disconnect: DisconnectUsecase,
    application: Application
) : AndroidViewModel(application) {

    val uiState: MutableLiveData<UIState> by lazy { MutableLiveData(UIState()) }
    private val networkObserver: NetworkObserver by lazy { NetworkManager(application.applicationContext) }

    fun launchObservers() {
        viewModelScope.launch(Dispatchers.IO) {
            launch { loadLocalData() }
            launch { loadOnlineData() }
            launch { observeSocketConnection() }
            launch { observeNetworkConnection() }
        }
    }

    private suspend fun observeSocketConnection() {
        connectionObserver.invoke().collectLatest {
            if (it.error != null) {
                uiState.postValue(uiState.value!!.copy(state = OFFLINE))
            }
            else if (!it.connected) {
                runDelayed {
                    uiState.postValue(uiState.value!!.copy(state = OFFLINE))  // Socket does not respond after disconnection - artifical disconnection
                }
            }
        }
    }

    private suspend fun loadLocalData() {
        Log.i("LTS_TAG", "loadLocalData")
        localDataObserver.invoke().collectLatest {
            Log.i("LTS_TAG", "loadLocalData: $it")
            uiState.postValue(uiState.value!!.copy(data = it)) }
    }

    private suspend fun observeNetworkConnection() {
        networkObserver.observe().collectLatest {
            when(it) {
                NetworkObserver.Status.Unavailable -> { disconnect();  uiState.postValue(uiState.value!!.copy(state = OFFLINE)) }
                NetworkObserver.Status.Lost -> { disconnect(); uiState.postValue(uiState.value!!.copy(state = OFFLINE)) }
                else -> Unit
            }
        }
    }

    private fun saveDataToLocalDB(list: List<InvestItem>) = viewModelScope.launch(Dispatchers.IO) { saveDataUsecase.invoke(list) }

    fun connect() {
        uiState.postValue(uiState.value!!.copy(state = CONNECTING))
        connect.invoke()
    }

    fun disconnect() {
        uiState.postValue(uiState.value!!.copy(state = DISCONNECTING))
        runDelayed { uiState.postValue(uiState.value!!.copy(state = OFFLINE)) }    // Socket does not respond after disconnection - artifical disconnection
        disconnect.invoke()
    }

    private fun loadOnlineData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataObserver.invoke().collect {
                if (it.isEmpty())  {
                    if (uiState.value!!.state != OFFLINE) uiState.postValue(uiState.value!!.copy(state = OFFLINE))
                }
                else {
                    Log.i("LTS_TAG", "Data : ${it.size}")
                    Log.i("LTS_TAG", "Data : $it")
                    if (uiState.value!!.state != DISCONNECTING) uiState.postValue(uiState.value!!.copy(state = ONLINE))
                    saveDataToLocalDB(it)
                }
            }
        }
    }

    private fun runDelayed(func: () -> Unit ) { Handler(Looper.getMainLooper()).postDelayed(1000) {func()} }
}