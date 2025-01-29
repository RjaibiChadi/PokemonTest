package com.test.pokemon.presentation.pokemonlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.test.pokemon.R
import com.test.pokemon.common.Content
import com.test.pokemon.common.toLazyPagingItems
import com.test.pokemon.domain.model.Pokemon
import com.test.pokemon.presentation.Screen
import com.test.pokemon.presentation.pokemonlist.components.PokemonItem
import com.test.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonScreen(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val pokemons = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = pokemons.loadState) {
        if (pokemons.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (pokemons.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    PokemonListContent(
        pokemons = pokemons,
        onItemClick = { pokemon ->
            navController.navigate(Screen.PokemonDetailScreen.route + "/${pokemon.name}")
        }
    )


}

@Composable
private fun PokemonListContent(
    pokemons: LazyPagingItems<Pokemon>,
    onItemClick: (Pokemon) -> Unit
) {
    Content(
        toolbarTitle = stringResource(R.string.app_name),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (pokemons.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(pokemons) { pokemon ->
                        if (pokemon != null)
                            PokemonItem(
                                modifier = Modifier.fillMaxWidth(),
                                pokemon = pokemon,
                                onItemClick = onItemClick
                            )
                    }
                    item {
                        if (pokemons.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListPreview() {
    PokemonTheme {
        PokemonListContent(
            pokemons = fakeLazyPagingItems(),
            onItemClick = {},
        )
    }
}

@Composable
fun fakeLazyPagingItems(): LazyPagingItems<Pokemon> {
    val fakePokemonList = List(20) { i -> Pokemon(id = i, name = "Pokemon", url = "") }
    return fakePokemonList.toLazyPagingItems()
}