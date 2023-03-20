package com.ktxdevelopment.websocket.flow.local

import com.ktxdevelopment.websocket.repo.LocalRepository
import javax.inject.Inject

class ObserveLocalDataUsecase @Inject constructor(private val repo: LocalRepository) {
    operator fun invoke() = repo.getAllInvests()
}