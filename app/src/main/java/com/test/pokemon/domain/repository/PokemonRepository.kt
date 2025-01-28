package com.test.pokemon.domain.repository

import com.test.pokemon.data.remote.PokemonDto
import com.test.pokemon.data.remote.dto.PokemonDetailDto
import com.test.pokemon.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonByName(name: String): PokemonDetailDto
}