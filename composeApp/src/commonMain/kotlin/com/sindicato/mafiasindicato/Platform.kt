package com.sindicato.mafiasindicato

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform