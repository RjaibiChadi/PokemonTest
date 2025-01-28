package com.test.pokemon.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.pokemon.presentation.pokemondetail.PokemonDetailScreen
import com.test.pokemon.presentation.pokemonlist.PokemonScreen
import com.test.pokemon.presentation.pokemonlist.PokemonViewModel
import com.test.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PokemonListScreen.route
                    ){
                        composable(
                            route = Screen.PokemonListScreen.route
                        ){
                            PokemonScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = Screen.PokemonDetailScreen.route + "/{pokemonName}"
                        ){
                            PokemonDetailScreen()
                        }
                    }
                }


            }
        }
    }
}
