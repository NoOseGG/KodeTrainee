package com.example.domain.repository

import com.example.domain.CharacterDetails
import com.example.domain.model.Character

interface CharacterDetailsRepository {

    suspend fun character(id: Int): Result<CharacterDetails>
}