package com.ktxdevelopment.websocket.repo.state

sealed class Resource<T> {
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val errorMessage: String) : Resource<T>()
}