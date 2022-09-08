package com.example.data.retrofit

import com.example.domain.model.Characters
import retrofit2.http.GET


interface RickAndMortyService {

    @GET("character")
    suspend fun characters(): Characters
}