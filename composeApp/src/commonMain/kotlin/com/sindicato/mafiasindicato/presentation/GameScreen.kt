package com.sindicato.mafiasindicato.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameScreen(
    onNavigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detail Screen", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onNavigateToHome) {
            Text("Back to Home")
        }
    }
}


@Preview
@Composable
fun GameScreenPreview() {
    MaterialTheme {
        GameScreen(
            onNavigateToHome = {}
        )
    }
}