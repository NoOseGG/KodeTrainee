package com.example.domain.repository

import com.example.domain.model.Characters

interface CharacterRepository {

    suspend fun characters(): Result<Characters>

    suspend fun speciesCharacters(species: String): Result<Characters>

    suspend fun searchCharacters(query: String): Result<Characters>
}