package com.example.data.retrofit

import com.example.domain.CharacterDetails
import com.example.domain.model.Characters
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character")
    suspend fun characters(
        @Query("page") page: Int
    ): Characters

    @GET("character")
    suspend fun speciesCharacters(
        @Query("page") page: Int,
        @Query("species") species: String,
        @Query("name") searchBy: String
    ): Characters

    @GET("character")
    suspend fun searchCharacters(
        @Query("page") page: Int,
        @Query("name") query: String
    ): Characters

    @GET("character/{id}")
    suspend fun character(
        @Path("id") id: Int
    ): CharacterDetails

}