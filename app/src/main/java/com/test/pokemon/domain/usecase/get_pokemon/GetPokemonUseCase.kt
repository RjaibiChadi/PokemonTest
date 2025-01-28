package com.test.pokemon.domain.usecase.get_pokemon

import android.util.Log
import com.test.pokemon.common.Resource
import com.test.pokemon.data.mapper.toPokemonDetail
import com.test.pokemon.data.remote.dto.PokemonDetailDto
import com.test.pokemon.domain.model.PokemonDetail
import com.test.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetail>> = flow {
        try {
            emit(Resource.Loading())
            val pokemon = repository.getPokemonByName(name).toPokemonDetail()
            emit(Resource.Success(pokemon))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("server problem"))
        }
    }
}