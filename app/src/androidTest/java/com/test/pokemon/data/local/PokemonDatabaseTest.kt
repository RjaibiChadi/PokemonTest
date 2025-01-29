package com.test.pokemon.data.local

import androidx.paging.PagingSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class PokemonDatabaseTest {

    // Rule for Hilt dependency injection in tests
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Inject the test database instance
    @Inject
    @Named("test_db")
    lateinit var database: PokemonDatabase

    // DAO instance to interact with the database
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun setUp() {
        // Inject dependencies before each test
        hiltRule.inject()
        // Clear all tables to ensure a clean state
        database.clearAllTables()
        // Initialize DAO for database operations
        pokemonDao = database.dao
    }

    @After
    fun closeDb() {
        // Close the database after each test
        database.close()
    }

    @Test
    fun testUpsertAllAndPagingSource() = runBlocking {

        // Insert test data into the database
        pokemonDao.upsertAll(pokemons)

        // Retrieve data using the PagingSource
        val pagingSource = pokemonDao.pagingSource()
        val result = (pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page).data

        // Assert that the inserted data is correctly retrieved
        assertEquals(2, result.size)
        assertEquals("Pikachu", result[0].name)
        assertEquals("Charmander", result[1].name)
    }

    @Test
    fun testClearAll() = runBlocking {

        // Insert test data into the database
        pokemonDao.upsertAll(pokemons)

        // Clear all data from the database
        pokemonDao.clearAll()

        // Retrieve data using the PagingSource after deletion
        val pagingSource = pokemonDao.pagingSource()
        val result = (pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page).data

        // Assert that the database is empty after clearing
        assertEquals(0, result.size)
    }

    companion object {
      private  val pokemons = listOf(
            PokemonEntity(id = 1, name = "Pikachu", url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonEntity(id = 2, name = "Charmander", url = "https://pokeapi.co/api/v2/pokemon/2/")
        )
    }
}
