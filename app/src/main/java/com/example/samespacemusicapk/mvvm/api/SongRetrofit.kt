package com.example.samespacemusicapk.mvvm.api

import com.example.samespacemusicapk.common.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongRetrofit {
    companion object{

        private val retrofit by lazy {
            val logging =HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }

        val api:SongApi by lazy {
            retrofit.create(SongApi::class.java)
        }

    }
}