package com.example.domain.model

data class Characters(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int
)