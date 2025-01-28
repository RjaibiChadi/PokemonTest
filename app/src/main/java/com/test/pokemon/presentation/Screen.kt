package com.test.pokemon.presentation

sealed class Screen(val route: String) {
    data object PokemonListScreen : Screen("pokemon_list_screen")
    data object PokemonDetailScreen : Screen("pokemon_detail_screen")

}