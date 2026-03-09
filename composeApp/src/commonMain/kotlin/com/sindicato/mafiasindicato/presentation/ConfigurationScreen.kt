package com.sindicato.mafiasindicato.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sindicato.mafiasindicato.domain.Player
import com.sindicato.mafiasindicato.domain.Role
import com.sindicato.mafiasindicato.presentation.state.GameState

@Composable
fun ConfigurationScreen(
    viewModel: MafiaViewModel,
    onNavigateToGame: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiGameState.collectAsState()

    // Local state for the dynamic list of names
    var playerNames by remember { mutableStateOf(listOf("")) }

    val totalRoles = uiState.mafias + uiState.police + uiState.medics
    val isSelectionValid = totalRoles <= playerNames.size && playerNames.all { it.isNotBlank() }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Volver")
                }
                Button(
                    onClick = {
                        viewModel.onGameStart(playerNames)
                        onNavigateToGame()
                    },
                    modifier = Modifier.weight(2.5f),
                    enabled = isSelectionValid
                ) {
                    Text("Start Game (${playerNames.size} Players)")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("Players Configuration", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Dynamic Player Inputs
            itemsIndexed(playerNames) { index, name ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = name,
                        onValueChange = { newName ->
                            val newList = playerNames.toMutableList()
                            newList[index] = newName
                            playerNames = newList
                        },
                        label = { Text("Player ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            if (playerNames.size > 1) {
                                playerNames = playerNames.toMutableList().apply { removeAt(index) }
                            }
                        },
                        enabled = playerNames.size > 1
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }

            item {
                TextButton(
                    onClick = { if (playerNames.size < 20) playerNames = playerNames + "" },
                    enabled = playerNames.size < 20
                ) {
                    Icon(Icons.Default.Add, null)
                    Text("Add Player")
                }
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp)) }

            // Advanced Settings
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Advanced Settings", modifier = Modifier.weight(1f))
                    Switch(
                        checked = uiState.advanced,
                        onCheckedChange = { viewModel.onAdvancedChange(it) }
                    )
                }
            }

            item {
                AnimatedVisibility(visible = uiState.advanced) {
                    Column {
                        RoleCounter("Mafias", uiState.mafias) { viewModel.onMafiasChange(it) }
                        RoleCounter("Police", uiState.police) { viewModel.onPoliceChange(it) }
                        RoleCounter("Medics", uiState.medics) { viewModel.onMedicsChange(it) }

                        if (totalRoles > playerNames.size) {
                            Text(
                                "Error: Too many roles for the player count!",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoleCounter(label: String, count: Int, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = { if (count > 0) onCountChange(count - 1) }) { Text("-") }
            Text("$count", modifier = Modifier.padding(horizontal = 16.dp))
            OutlinedButton(onClick = { onCountChange(count + 1) }) { Text("+") }
        }
    }
}

@Preview
@Composable
fun ConfigurationScreenPreview() {
    MaterialTheme {
        ConfigurationScreen(
            viewModel = MafiaViewModel(),
            onNavigateToGame = {},
            onNavigateBack = {}
        )
    }
}
