package com.test.pokemon

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.test.pokemon.data.local.PokemonDatabase
import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.remote.PokemonApi
import com.test.pokemon.data.repository.PokemonRemoteMediator
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

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediatorTest {

    // Instance of the class to be tested
    private lateinit var pokemonRemoteMediator: PokemonRemoteMediator

    // Mocked dependencies for API and Database
    private val mockApi = mock<PokemonApi>()
    private val mockDb = mock<PokemonDatabase>()


    @Before
    fun setup() {

        // Initialize the RemoteMediator with mocked dependencies
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

        // Execute the load function with PREPEND type (indicating a request for older data)
        val result = pokemonRemoteMediator.load(LoadType.PREPEND, pagingState)

        // Verify
        verify(mockApi, never()).getPokemons(any(), any())

        // Assert that the result indicates the end of pagination
        assert(result is RemoteMediator.MediatorResult.Success)
        assertEquals(true, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load - http error`() = runBlocking {

        // Mock an API error by making the API call throw an HttpException
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

        // Verify that the API call was made
        verify(mockApi).getPokemons(any(), any())

        // Assert that the result indicates an error
        assert(result is RemoteMediator.MediatorResult.Error)
    }

}