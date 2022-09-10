package com.example.domain.repository

import com.example.domain.model.Characters

interface CharacterRepository {

    suspend fun characters(page: Int, species: String = "", searchBy: String = ""): Result<Characters>

}