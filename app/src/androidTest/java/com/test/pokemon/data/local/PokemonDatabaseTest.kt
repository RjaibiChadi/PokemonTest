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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: PokemonDatabase

    private lateinit var pokemonDao: PokemonDao

    @Before
    fun setUp() {
        hiltRule.inject()
        database.clearAllTables()
        pokemonDao = database.dao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testUpsertAllAndPagingSource() = runBlocking {
        // Créez des données de test
        val pokemons = listOf(
            PokemonEntity(id = 1, name = "Pikachu", url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonEntity(id = 2, name = "Charmander", url = "https://pokeapi.co/api/v2/pokemon/2/")
        )

        // Insérez les données
        pokemonDao.upsertAll(pokemons)

        // Vérifiez les données dans la PagingSource
        val pagingSource = pokemonDao.pagingSource()
        val result = (pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page).data

        assertEquals(2, result.size)
        assertEquals("Pikachu", result[0].name)
        assertEquals("Charmander", result[1].name)
    }

    @Test
    fun testClearAll() = runBlocking {
        // Créez des données de test
        val pokemons = listOf(
            PokemonEntity(id = 1, name = "Pikachu", url = "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonEntity(id = 2, name = "Charmander", url = "https://pokeapi.co/api/v2/pokemon/2/")
        )

        // Insérez les données
        pokemonDao.upsertAll(pokemons)

        // Supprimez toutes les données
        pokemonDao.clearAll()

        // Vérifiez que les données ont été supprimées
        val pagingSource = pokemonDao.pagingSource()
        val result = (pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page).data

        assertEquals(0, result.size)
    }
}