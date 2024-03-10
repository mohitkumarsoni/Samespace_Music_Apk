package com.example.samespacemusicapk.common

sealed class SongResource<T>(var data: T? = null, var message: String? = null) {
    class Success<T>(data: T) : SongResource<T>(data)
    class Loading<T>() : SongResource<T>()
    class Error<T>(data: T? = null, message: String) : SongResource<T>(data, message)
}