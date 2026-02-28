package com.sindicato.mafiasindicato.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.compose.ui.unit.sp
import com.sindicato.mafiasindicato.domain.Player
import com.sindicato.mafiasindicato.domain.Role
import com.sindicato.mafiasindicato.presentation.state.GameState
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight

@Composable
fun GameScreen(
    gameState: GameState, // Passing the list of state objects
    onNavigateToHome: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { gameState.players.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Button(onClick = onNavigateToHome) {
                Text("Back")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Lateral (Horizontal) Scrollable Area
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Left Arrow
                IconButton(
                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                    enabled = pagerState.currentPage > 0
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous")
                }

                // Individual Player Box (Pager)
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f).height(400.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp
                ) { page ->
                    PlayerBox(gameState.players[page])
                }

                // Right Arrow
                IconButton(
                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                    enabled = pagerState.currentPage < gameState.players.size - 1
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next")
                }
            }
        }
    }
}


@Composable
fun PlayerBox(player: Player) {
    var revealed by remember { mutableStateOf(player.isRevealed) }

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(player.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(40.dp))

            if (!revealed) {
                Button(
                    modifier = Modifier
                        .size(150.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    onClick = { revealed = true }
                ) {
                    Text("Show Role", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            } else {

                OutlinedButton(onClick = { revealed = false }) {
                    Text("Hide")
                }
            }
        }
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
                )
            ),
            onNavigateToHome = {}
        )
    }
}