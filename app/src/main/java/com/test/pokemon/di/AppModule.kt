package com.test.pokemon.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.test.pokemon.data.local.PokemonDatabase
import com.test.pokemon.data.local.PokemonEntity
import com.test.pokemon.data.remote.PokemonApi
import com.test.pokemon.data.repository.PokemonRemoteMediator
import com.test.pokemon.common.Constants.BASE_URL
import com.test.pokemon.common.Constants.DATABASE_NAME
import com.test.pokemon.data.repository.PokemonRepositoryImpl
import com.test.pokemon.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonDataBase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePokemonPager(
        pokemonDb: PokemonDatabase,
        pokemonApi: PokemonApi
    ): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = PokemonRemoteMediator(
                pokemonDb = pokemonDb,
                pokemonApi = pokemonApi
            ),
            pagingSourceFactory = {
                pokemonDb.dao.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApi): PokemonRepository {
        return PokemonRepositoryImpl(api)
    }
}