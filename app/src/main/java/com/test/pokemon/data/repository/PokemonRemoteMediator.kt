@file:OptIn(ExperimentalPagingApi::class)

package com.test.pokemon.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.test.pokemon.data.local.PokemonDatabase
import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.mapper.toPokemonEntity
import com.test.pokemon.data.remote.PokemonApi
import kotlinx.coroutines.time.delay
import retrofit2.HttpException
import java.io.IOException
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

// A RemoteMediator to fetch Pokémon data from an API and store it in a local database
class PokemonRemoteMediator(
    private val pokemonDb: PokemonDatabase,
    private val pokemonApi: PokemonApi
) : RemoteMediator<Int, PokemonEntity>() {
    private var delayTime = 2000.milliseconds
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {
            // Determine the offset based on the load type
            val offset = when (loadType) {
                LoadType.REFRESH -> 0 // When refreshing, start from the first page
                LoadType.PREPEND ->
                    // If we are prepending (trying to load before the first item), there's nothing to load
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    // When appending (loading next page), get the last item in the list
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            delay(delayTime.toJavaDuration())
            // Fetch Pokémon data from the API
            val pokemons = pokemonApi.getPokemons(
                offset = offset,
                limit = state.config.pageSize
            )
            // Perform database operations inside a transaction
            pokemonDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDb.dao.clearAll()
                }
                val pokemonEntities = pokemons.results.map { it.toPokemonEntity() }
                pokemonDb.dao.upsertAll(pokemonEntities)
            }
            // Return a success result, specifying whether we reached the end of the pagination
            MediatorResult.Success(
                endOfPaginationReached = pokemons.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}