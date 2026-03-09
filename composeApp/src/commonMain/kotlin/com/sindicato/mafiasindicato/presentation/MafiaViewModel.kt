package com.sindicato.mafiasindicato.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sindicato.mafiasindicato.domain.Player
import com.sindicato.mafiasindicato.domain.usecase.RoleAsignUseCase
import com.sindicato.mafiasindicato.domain.usecase.SaveConfigurationUseCase
import com.sindicato.mafiasindicato.presentation.state.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MafiaViewModel(
    private val saveConfigurationUseCase: SaveConfigurationUseCase,
    private val roleAsignUseCase: RoleAsignUseCase
): ViewModel() {

    private val _uiGameState = MutableStateFlow(GameState(listOf<Player>(), 1, 1, 1))
    val uiGameState: StateFlow<GameState> = _uiGameState.asStateFlow()


    fun onGameStart(playerNames: List<String>){

        val newMafias = roleAsignUseCase.calculateMafias(_uiGameState.value.mafias, _uiGameState.value.advanced, playerNames.size)
        val newPolice = _uiGameState.value.police
        val newMedics = _uiGameState.value.medics


        viewModelScope.launch {

            /* Role assignment */
            val newPlayers = roleAsignUseCase.assignRoles(
                playerNames = playerNames,
                mafias = newMafias,
                police = newPolice,
                medics = newMedics
            )

            /* Update game configuration */
            onGameConfigUpdate(newPlayers, newMafias, newPolice, newMedics)
        }
    }


    fun onGameConfigUpdate(newPlayers: List<Player>, newMafias: Int, newPolice: Int, newMedics: Int){
        
        viewModelScope.launch{

            saveConfigurationUseCase.onSave(newPlayers, newMafias, newPolice, newMedics)
        }
    }

    fun onAdvancedChange(advanced: Boolean) = _uiGameState.update {it.copy(advanced = advanced)}

    fun onMafiasChange(mafias: Int) = _uiGameState.update {it.copy(mafias = mafias)}

    fun onPoliceChange(police: Int) = _uiGameState.update {it.copy(police = police)}

    fun onMedicsChange(medics: Int) = _uiGameState.update {it.copy(medics = medics)}

}
