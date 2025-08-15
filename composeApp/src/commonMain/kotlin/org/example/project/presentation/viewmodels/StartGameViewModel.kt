package org.example.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.core.const.MAX_GRID_LENGTH
import org.example.project.core.const.MIN_GRID_LENGTH
import org.example.project.core.const.PLAYER1_DEFAULT_NAME
import org.example.project.core.const.PLAYER2_DEFAULT_NAME

data class StartGameState(
    val gridLength: Int = 3,
    val firstPlayerName: String = "",
    val secondPlayerName: String = "",
    val errorMsg: String = ""
) {
    val firstPlayerNameOrDefault get() = firstPlayerName.ifEmpty { PLAYER1_DEFAULT_NAME }
    val secondPlayerNameOrDefault get() = secondPlayerName.ifEmpty { PLAYER2_DEFAULT_NAME }
}

class StartGameViewModel: ViewModel() {
    private val _state = MutableStateFlow(StartGameState())
    val state = _state.asStateFlow()

    fun onPlusLength() {
        _state.update { oldState ->
            val oldGridLength = oldState.gridLength
            oldState.copy(
                gridLength = if (oldGridLength < MAX_GRID_LENGTH) oldGridLength + 1 else oldGridLength,
                errorMsg = if (oldGridLength == MAX_GRID_LENGTH) "Limite máximo atingido" else ""
            )
        }
    }

    fun onMinusLength() {
        _state.update { oldState ->
            val oldGridLength = oldState.gridLength
            oldState.copy(
                gridLength = if (oldGridLength > MIN_GRID_LENGTH) oldGridLength - 1 else oldGridLength,
                errorMsg = if (oldGridLength == MIN_GRID_LENGTH) "Limite minimo atingido" else ""

            )
        }
    }

    fun onChangeFirstPlayerName(
        name: String
    ) {
        _state.update { oldState ->
            oldState.copy(
                firstPlayerName = name
            )
        }
    }

    fun onChangeSecondPlayerName(
        name: String
    ) {
        _state.update { oldState ->
            oldState.copy(
                secondPlayerName = name
            )
        }
    }

    fun startGame() {

    }
}