package com.example.domain.repository

import com.example.domain.model.CharacterDetails

interface CharacterDetailsRepository {

    suspend fun character(id: Int): Result<CharacterDetails>
}