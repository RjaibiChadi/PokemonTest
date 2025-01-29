package com.test.pokemon.presentation.pokemondetail


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.pokemon.R
import com.test.pokemon.common.Content
import com.test.pokemon.domain.model.PokemonDetail
import com.test.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonDetailScreen(
    back: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    PokemonListContent(
        back = back,
        state = state
    )
}

@Composable
private fun PokemonListContent(
    back: () -> Unit,
    state: PokemonDetailState,
) {
    Content(
        toolbarTitle = stringResource(R.string.detail),
        leftToolbarClick = back
    ) {
        state.pokemon?.let { pokemon ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun PokemonDetailPreview() {
    PokemonTheme {
        PokemonListContent(
            back = {},
            state = PokemonDetailState(
                pokemon =
                PokemonDetail(
                    id = 1, name = "Pikachu",
                    baseExperience = 6,
                    height = 5,
                    isDefault = false,
                    order = 2,
                )
            )
        )
    }
}