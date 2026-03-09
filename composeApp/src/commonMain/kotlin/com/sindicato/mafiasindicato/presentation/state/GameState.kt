package com.sindicato.mafiasindicato.presentation.state

import com.sindicato.mafiasindicato.domain.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val mafias: Int = 1,
    val police: Int = 1,
    val medics: Int = 1,
    val advanced: Boolean = false
)
