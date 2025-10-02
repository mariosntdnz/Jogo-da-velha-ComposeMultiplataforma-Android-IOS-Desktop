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
import org.example.project.core.const.PLAYER1_MARKER
import org.example.project.core.const.PLAYER2_DEFAULT_NAME
import org.example.project.core.const.PLAYER2_MARKER
import org.example.project.data.repository.currentGame.UPSERT_ERROR
import org.example.project.domain.models.EMPTY_GAME_STATE
import org.example.project.domain.models.Player
import org.example.project.domain.useCase.GetHistoryUseCase
import org.example.project.domain.useCase.HistoryFilterType
import org.example.project.domain.useCase.UpsertGameUseCase

enum class StartGameType {
    Start,
    Restart,
    Delete,
    None;

    fun getLabel(): String {
        return when(this) {
            Start -> "Começar"
            Restart -> "Continuar jogo existente"
            Delete -> "Começar um novo jogo"
            None -> ""
        }
    }
}

data class StartGameState(
    internal val gameId: Long = 0L,
    val gridLength: Int = 3,
    val firstPlayer: Player = Player(id = 0, name = "", marker = PLAYER1_MARKER),
    val secondPlayer: Player = Player(id = 1, name = "", marker = PLAYER2_MARKER),
    val errorMsg: String = "",
    val startGameType: StartGameType = StartGameType.None,
    val hasStartGameType: Boolean = false,
    val newGameType: StartGameType = StartGameType.Delete
) {
    val hasNewGameType get() = newGameType == StartGameType.Delete
}

class StartGameViewModel(
    private val upsertGameUseCase: UpsertGameUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
): ViewModel() {
    private val _state = MutableStateFlow(StartGameState())
    val state = _state.asStateFlow()

    private var observeGameStateJob: Job? = null

    init {
        observeGameStateJob = viewModelScope.launch(Dispatchers.IO) {
            getHistoryUseCase(HistoryFilterType.OngoingGames).collect { newState ->
                withContext(Dispatchers.Main) {
                    _state.update { oldState ->
                        val startGameType =
                            if (newState.isEmpty()) {
                                StartGameType.Start
                            } else {
                                StartGameType.Restart
                            }
                        oldState.copy(
                            startGameType = startGameType,
                            hasStartGameType = startGameType == StartGameType.Restart
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
            val oldPlayer = oldState.firstPlayer
            oldState.copy(
                firstPlayer = oldPlayer.copy(
                    name = name
                )
            )
        }
    }

    fun onChangeSecondPlayerName(
        name: String
    ) {
        _state.update { oldState ->
            val oldPlayer = oldState.secondPlayer
            oldState.copy(
                secondPlayer = oldPlayer.copy(
                    name = name
                )
            )
        }
    }

    fun onNewGameClick(onFinish: (Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value
            val firstPlayer = if (state.firstPlayer.name.isEmpty()) {
                state.firstPlayer.copy(
                    name = PLAYER1_DEFAULT_NAME
                )
            } else {
                state.firstPlayer
            }
            val secondPlayer = if (state.secondPlayer.name.isEmpty()) {
                state.secondPlayer.copy(
                    name = PLAYER2_DEFAULT_NAME
                )
            } else {
                state.secondPlayer
            }
            val id = upsertGameUseCase(
                game = EMPTY_GAME_STATE.copy(
                    gridLength = state.gridLength,
                    firstPlayer = firstPlayer,
                    secondPlayer = secondPlayer,
                    currentPlayer = firstPlayer
                )
            )
            if (id != UPSERT_ERROR) {
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            gameId = id
                        )
                    }
                    onFinish(id)
                }
            }
        }
    }
}