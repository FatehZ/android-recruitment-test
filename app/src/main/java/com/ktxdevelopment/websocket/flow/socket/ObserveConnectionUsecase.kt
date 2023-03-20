package com.ktxdevelopment.websocket.flow.socket

import com.ktxdevelopment.websocket.repo.RemoteRepository
import javax.inject.Inject

class ObserveConnectionUsecase @Inject constructor(private val repo: RemoteRepository) {
    operator fun invoke() = repo.connectionState
}