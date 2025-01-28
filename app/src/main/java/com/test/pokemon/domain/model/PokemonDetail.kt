package com.test.pokemon.domain.model

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    val id: Int,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val name: String,
    val order: Int,
)