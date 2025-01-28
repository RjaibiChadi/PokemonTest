package com.test.pokemon.presentation.pokemondetail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pokemon.common.Constants.POKEMON_NAME
import com.test.pokemon.common.Resource
import com.test.pokemon.domain.usecase.get_pokemon.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonUseCase: GetPokemonUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _state = mutableStateOf(PokemonDetailState())
    val state = _state

    init {

        savedStateHandle.get<String>(POKEMON_NAME)?.let { name ->
            getPokemon(name)
        }
    }

    private fun getPokemon(name: String) {
        pokemonUseCase(name).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = PokemonDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = PokemonDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = PokemonDetailState(pokemon = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}