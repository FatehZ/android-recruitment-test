package com.ktxdevelopment.websocket.flow.socket

import com.ktxdevelopment.websocket.repo.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DisconnectUsecase @Inject constructor(private val repo: RemoteRepository) {
    operator fun invoke() = repo.disconnect()
}