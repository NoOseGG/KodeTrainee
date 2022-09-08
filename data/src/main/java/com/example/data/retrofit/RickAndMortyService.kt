package com.example.data.retrofit

import com.example.domain.model.Characters
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character")
    suspend fun characters(): Characters

    @GET("character")
    suspend fun speciesCharacters(
        @Query("species") species: String
    ): Characters

}