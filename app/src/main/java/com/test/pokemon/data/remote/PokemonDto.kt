package com.test.pokemon.data.remote

data class PokemonDto(
    val name: String,
    val url: String
)
// Function to extract an integer ID from the given URL
fun extractIdFromUrl(url: String): Int {
    return url.trimEnd('/').split('/').last().toInt()
}