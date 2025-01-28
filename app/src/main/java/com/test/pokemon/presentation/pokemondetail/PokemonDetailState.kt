package com.test.pokemon.presentation.pokemondetail

import com.test.pokemon.domain.model.PokemonDetail

data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail? = null,
    val error: String = ""
)
