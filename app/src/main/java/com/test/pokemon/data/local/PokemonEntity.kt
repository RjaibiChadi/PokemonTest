package com.test.pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PokemonEntity(

    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
)
