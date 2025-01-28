package com.test.pokemon.data.remote.dto

import com.test.pokemon.data.remote.PokemonDto

data class PokemonResultsDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)

