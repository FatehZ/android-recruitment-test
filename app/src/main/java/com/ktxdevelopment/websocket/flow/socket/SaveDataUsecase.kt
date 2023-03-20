package com.ktxdevelopment.websocket.flow.socket

import com.ktxdevelopment.websocket.model.remote.InvestItem
import com.ktxdevelopment.websocket.repo.LocalRepository
import com.ktxdevelopment.websocket.repo.RemoteRepository
import javax.inject.Inject

class SaveDataUsecase @Inject constructor(private val repo: LocalRepository) {
    suspend operator fun invoke(list: List<InvestItem>) = repo.saveInvests(list)
}