package com.sindicato.mafiasindicato.domain.usecase

import com.sindicato.mafiasindicato.domain.Player

class SaveConfigurationUseCase(){

    suspend fun onSave(players: List<Player>, mafias: Int, police: Int, medics: Int){
        /* Save to database */
    }

}