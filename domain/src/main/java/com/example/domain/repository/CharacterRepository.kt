package com.example.domain.repository

interface CharacterRepository {

    fun characters(): Result<List<Character>>
}