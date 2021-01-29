package com.example.itunessearchapp.repository

import com.example.itunessearchapp.api.SongsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val baseUrl = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val songsApi: SongsAPI = retrofit.create(SongsAPI::class.java)
}