package org.example.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.core.const.MAX_GRID_LENGTH
import org.example.project.core.const.MIN_GRID_LENGTH
import org.example.project.core.const.PLAYER1_DEFAULT_NAME
import org.example.project.core.const.PLAYER2_DEFAULT_NAME
import org.example.project.domain.models.EMPTY_GAME_STATE
import org.example.project.domain.models.GameState
import org.example.project.domain.useCase.DeleteCurrentGameUseCase
import org.example.project.domain.useCase.GetCurrentGameUseCase

enum class StartGameType {
    Start,
    Restart,
    Delete,
    None;

    fun getLabel(): String {
        return when(this) {
            Start -> "Começar"
            Restart -> "Continuar jogo atual"
            Delete -> "Começar um novo jogo"
            None -> ""
        }
    }
}

data class StartGameState(
    val gridLength: Int = 3,
    val firstPlayerName: String = "",
    val secondPlayerName: String = "",
    val errorMsg: String = "",
    val startGameType: StartGameType = StartGameType.None,
    val newGameType: StartGameType = StartGameType.None
) {
    val firstPlayerNameOrDefault get() = firstPlayerName.ifEmpty { PLAYER1_DEFAULT_NAME }
    val secondPlayerNameOrDefault get() = secondPlayerName.ifEmpty { PLAYER2_DEFAULT_NAME }
    val hasNewGameType get() = newGameType == StartGameType.Delete
}

class StartGameViewModel(
    private val getCurrentGameUseCase: GetCurrentGameUseCase,
    private val deleteCurrentGameUseCase: DeleteCurrentGameUseCase
): ViewModel() {
    private val _state = MutableStateFlow(StartGameState())
    val state = _state.asStateFlow()

    private var observeGameStateJob: Job? = null

    init {
        observeGameStateJob = viewModelScope.launch(Dispatchers.IO) {
            getCurrentGameUseCase(GAME_ID).collect { newState ->
                withContext(Dispatchers.Main) {
                    _state.update { oldState ->
                        val startGameType =
                            if (newState?.currentGrid?.values?.flatten()?.any { it.isChecked } == true) {
                                StartGameType.Restart
                            } else {
                                StartGameType.Start
                            }
                        val newGameType = if (startGameType == StartGameType.Restart) {
                            StartGameType.Delete
                        } else {
                            StartGameType.None
                        }
                        oldState.copy(
                            startGameType = startGameType,
                            newGameType = newGameType
                        )
                    }
                }
            }
        }
    }


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

    fun onNewGameClick() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCurrentGameUseCase(EMPTY_GAME_STATE)
        }
    }
}