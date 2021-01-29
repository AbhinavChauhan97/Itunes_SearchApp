package com.example.itunessearchapp.api

import com.example.itunessearchapp.model.Song
import com.example.itunessearchapp.model.SongsSearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongsAPI {

    @GET("search/")
    suspend fun getSongsFor(
        @Query("term") artistName: String,
        @Query("entity") contentType: String
    ): Response<SongsSearchResult>


}