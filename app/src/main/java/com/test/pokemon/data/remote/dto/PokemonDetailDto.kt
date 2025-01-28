package com.test.pokemon.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    val id:Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    @SerializedName("is_default")
    val isDefault:Boolean,
    val name: String,
    val order: Int,
)
