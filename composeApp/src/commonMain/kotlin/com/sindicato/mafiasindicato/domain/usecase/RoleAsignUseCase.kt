package com.sindicato.mafiasindicato.domain.usecase

import com.sindicato.mafiasindicato.domain.Player
import com.sindicato.mafiasindicato.domain.Role

class RoleAsignUseCase {
    
    fun assignRoles(
        playerNames: List<String>,
        mafias: Int,
        police: Int,
        medics: Int
    ): List<Player> {
        val roles = mutableListOf<Role>()
        
        repeat(mafias) { roles.add(Role.MAFIA) }
        repeat(police) { roles.add(Role.POLICE) }
        repeat(medics) { roles.add(Role.MEDIC) }
        
        val remainingCount = playerNames.size - roles.size
        repeat(remainingCount.coerceAtLeast(0)) {
            roles.add(Role.PEOPLE)
        }
        
        val shuffledRoles = roles.shuffled()
        
        return playerNames.take(shuffledRoles.size).mapIndexed { index, name ->
            Player(name, shuffledRoles[index])
        }
    }


    fun calculateMafias(mafias: Int, advanced: Boolean, players: Int): Int {
        return if(!advanced){
            mafias
        } else {
            players/3 // A mafia every three players
        }

    }
}