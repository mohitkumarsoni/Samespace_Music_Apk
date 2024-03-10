package com.example.samespacemusicapk.mvvm.api

import com.example.samespacemusicapk.ui_layers.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface SongApi {

    @GET("items/songs")
    suspend fun getSongList(): Response<ResponseData>

}