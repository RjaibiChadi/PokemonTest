package com.test.pokemon

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.test.pokemon.data.local.PokemonDao
import com.test.pokemon.data.local.PokemonDatabase
import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.remote.PokemonApi
import com.test.pokemon.data.remote.PokemonDto
import com.test.pokemon.data.remote.PokemonRemoteMediator
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediatorTest {

    private lateinit var pokemonRemoteMediator: PokemonRemoteMediator
    private val mockApi = mock<PokemonApi>()
    private val mockDb = mock<PokemonDatabase>()


    @Before
    fun setup() {

        pokemonRemoteMediator = PokemonRemoteMediator(
            pokemonApi = mockApi,
            pokemonDb = mockDb
        )
    }




    @Test
    fun `load - prepend - end of pagination`() = runBlocking {
        // Test PagingState
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // Execute
        val result = pokemonRemoteMediator.load(LoadType.PREPEND, pagingState)

        // Verify
        verify(mockApi, never()).getPokemons(any(), any()) // API should not be called for PREPEND

        // Assert
        assert(result is RemoteMediator.MediatorResult.Success)
        assertEquals(true, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load - http error`() = runBlocking {
        // Mock API error
        val exception = mock<HttpException>()
        `when`(mockApi.getPokemons(any(), any())).thenThrow(exception)

        // Test PagingState
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // Execute
        val result = pokemonRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Verify
        verify(mockApi).getPokemons(any(), any())

        // Assert
        assert(result is RemoteMediator.MediatorResult.Error)
    }

}