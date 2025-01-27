package com.test.pokemon.data.remote

data class PokemonResultsDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)

