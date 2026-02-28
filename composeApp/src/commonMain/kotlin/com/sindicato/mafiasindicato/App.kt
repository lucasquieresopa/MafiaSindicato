package com.sindicato.mafiasindicato


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sindicato.mafiasindicato.presentation.GameScreen
import com.sindicato.mafiasindicato.presentation.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
object Game
@Composable
fun App() {
    MaterialTheme {
        // Surface ensures the background color is applied consistently across platforms
        // and handles content color (text) automatically.
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Home
            ) {
                composable<Home> {
                    HomeScreen(
                        onNavigateToGame = { navController.navigate(Game) }

                    )
                }

                composable<Game> {
                    GameScreen(
                        onNavigateToHome = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}






