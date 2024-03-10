package com.example.samespacemusicapk.mvvm.repository

import com.example.samespacemusicapk.mvvm.api.SongRetrofit

class SongRepository {

    suspend fun getSongList() = SongRetrofit.api.getSongList()

}