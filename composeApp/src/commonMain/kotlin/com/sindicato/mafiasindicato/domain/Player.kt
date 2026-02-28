package com.sindicato.mafiasindicato.domain

data class Player(
    val name: String,
    val role: Role,
    val isRevealed: Boolean = false
)