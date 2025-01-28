package com.test.pokemon.data.remote

import com.test.pokemon.data.remote.dto.PokemonDetailDto
import com.test.pokemon.data.remote.dto.PokemonResultsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): PokemonResultsDto

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String,
    ): PokemonDetailDto


}