package com.example.data.repository

import com.example.data.retrofit.RickAndMortyService
import com.example.domain.CharacterDetails
import com.example.domain.repository.CharacterDetailsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDetailsRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
): CharacterDetailsRepository {
    override suspend fun character(id: Int): Result<CharacterDetails> {
        return kotlin.runCatching {
            rickAndMortyService.character(id)
        }
    }
}