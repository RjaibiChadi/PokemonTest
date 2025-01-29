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
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            delay(delayTime.toJavaDuration())

            val pokemons = pokemonApi.getPokemons(
                offset = offset,
                limit = state.config.pageSize
            )
            pokemonDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDb.dao.clearAll()
                }
                val pokemonEntities = pokemons.results.map { it.toPokemonEntity() }
                pokemonDb.dao.upsertAll(pokemonEntities)
            }

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