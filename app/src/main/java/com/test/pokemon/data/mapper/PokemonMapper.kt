package com.test.pokemon.data.mapper

import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.remote.PokemonDto
import com.test.pokemon.data.remote.extractIdFromUrl
import com.test.pokemon.domain.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = extractIdFromUrl(url),
        name = name,
        url = url
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        url = url
    )
}