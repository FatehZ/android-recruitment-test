package com.ktxdevelopment.websocket.mvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ktxdevelopment.websocket.flow.local.ObserveLocalDataUsecase
import com.ktxdevelopment.websocket.flow.socket.*
import com.ktxdevelopment.websocket.model.connection.DataState
import com.ktxdevelopment.websocket.model.connection.UIConnectionState.*
import com.ktxdevelopment.websocket.model.connection.UIState
import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.remote.net.NetworkManager
import com.ktxdevelopment.websocket.remote.net.NetworkObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    private val localData : ArrayList<InvestItem> by lazy { arrayListOf() }

    fun launchObservers() {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                // Observing internet connection
                networkObserver.observe().collectLatest {
                    when(it) {
                        NetworkObserver.Status.Unavailable -> { disconnect(); uiState.postValue(uiState.value!!.copy(dataType = DataState.LOCAL, data = localData, state = OFFLINE)) }
                        NetworkObserver.Status.Lost -> { disconnect(); uiState.postValue(uiState.value!!.copy(dataType = DataState.LOCAL, data = localData, state = OFFLINE)) }
                        else -> Unit
                    }
                }
            }

            // Observing Socket connection
            launch {
                connectionObserver.invoke().collectLatest {
                    if (it.error != null) uiState.postValue(uiState.value!!.copy(dataType = DataState.LOCAL, data = localData, state = OFFLINE))
                    else if (!it.connected) uiState.postValue(uiState.value!!.copy(dataType = DataState.LOCAL, data = localData, state = OFFLINE))
                }
            }

            loadOnlineData()

            launch(Dispatchers.IO) {
                localDataObserver.invoke().collectLatest {
                    localData.clear()
                    localData.addAll(it)
                }
            }
        }
    }

    private fun saveDataToLocalDB(list: List<InvestItem>) = viewModelScope.launch(Dispatchers.IO) { saveDataUsecase.invoke(list) }

    fun connect() {
        Log.i(TAG, "CONNECT")
        uiState.postValue(uiState.value!!.copy(state = CONNECTING))
        connect.invoke()
    }

    fun disconnect() {
        Log.i(TAG, "DISCONNECT")
        uiState.postValue(uiState.value!!.copy(state = DISCONNECTING))
        disconnect.invoke()
    }

    private fun loadOnlineData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataObserver.invoke().collectLatest {
                if (it.isEmpty()) {
                    Log.i(TAG, "Load Online --- OFFLINE")
                    if (uiState.value!!.dataType != DataState.LOCAL) uiState.postValue(uiState.value!!.copy(dataType = DataState.LOCAL ,data = localData, state = OFFLINE))
                }
                else {
                    Log.i(TAG, "Load Online --- ONLINE")
                    uiState.postValue(uiState.value!!.copy(state = ONLINE, data = it, dataType = DataState.ONLINE))
                    saveDataToLocalDB(it)
                }
            }
        }
    }

    private val TAG = "LTS_TAG"
}