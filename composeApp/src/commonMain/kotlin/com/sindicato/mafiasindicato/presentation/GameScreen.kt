package com.sindicato.mafiasindicato.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sindicato.mafiasindicato.domain.Player
import com.sindicato.mafiasindicato.domain.Role
import com.sindicato.mafiasindicato.presentation.state.GameState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameState: GameState, // Passing the list of state objects
    onNavigateToHome: () -> Unit
) {
    var showOptions by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mafia Sindicato") },
                actions = {
                    IconButton(onClick = { showOptions = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Opciones")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("En juego", style = MaterialTheme.typography.headlineMedium)
        }
    }

    if (showOptions) {
        val peopleCount = gameState.players.count { it.role == Role.PEOPLE }
        
        AlertDialog(
            onDismissRequest = { showOptions = false },
            title = { Text("Información de la Partida") },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Resumen de Roles:", style = MaterialTheme.typography.titleSmall)
                    Text("• Mafias: ${gameState.mafias}")
                    Text("• Policías: ${gameState.police}")
                    Text("• Médicos: ${gameState.medics}")
                    Text("• Ciudadanos: $peopleCount")
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("Lista de Jugadores:", style = MaterialTheme.typography.titleSmall)
                    gameState.players.forEach { player ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(player.name)
                            Text(player.role.name, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showOptions = false
                        onNavigateToHome()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar Juego")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOptions = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    MaterialTheme {
        GameScreen(
            GameState(
                listOf(
                    Player("Player 1", Role.MAFIA),
                    Player("Player 2", Role.MEDIC),
                    Player("Player 3", Role.POLICE),
                    Player("Player 4", Role.PEOPLE),
                ),
                mafias = 1,
                police = 1,
                medics = 1
            ),
            onNavigateToHome = {}
        )
    }
}
