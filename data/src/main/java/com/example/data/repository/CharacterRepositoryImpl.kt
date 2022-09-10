package com.example.data.repository

import com.example.data.retrofit.RickAndMortyService
import com.example.domain.model.Characters
import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : CharacterRepository {

    override suspend fun characters(page: Int, species: String, searchBy: String): Result<Characters> {
        return runCatching {
            rickAndMortyService.speciesCharacters(page, species, searchBy)
        }
    }
}