package com.test.pokemon.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.pokemon.domain.Pokemon
import com.test.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${pokemon.name} (${pokemon.id})",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PokemonItemPreview() {
    PokemonTheme {
        PokemonItem(
            pokemon = Pokemon(
                id = 1,
                name = "Bulbasaur",
                url = "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}