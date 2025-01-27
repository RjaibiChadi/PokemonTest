package com.test.pokemon.data.remote

data class PokemonDto(
    val name: String,
    val url: String
)
fun extractIdFromUrl(url: String): Int {
    return url.trimEnd('/').split('/').last().toInt()
}