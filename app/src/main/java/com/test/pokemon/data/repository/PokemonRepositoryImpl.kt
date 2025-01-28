package com.test.pokemon.data.repository

import com.test.pokemon.data.remote.PokemonApi
import com.test.pokemon.data.remote.dto.PokemonDetailDto
import com.test.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {
    override suspend fun getPokemonByName(name: String): PokemonDetailDto {
        return api.getPokemonByName(name)
    }
}