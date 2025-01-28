package com.test.pokemon.data.mapper

import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.remote.PokemonDto
import com.test.pokemon.data.remote.dto.PokemonDetailDto
import com.test.pokemon.data.remote.extractIdFromUrl
import com.test.pokemon.domain.model.Pokemon
import com.test.pokemon.domain.model.PokemonDetail

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

fun PokemonDetailDto.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        id = id,
        baseExperience = baseExperience,
        height = height,
        isDefault = isDefault,
        name = name,
        order = order
    )
}