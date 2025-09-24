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
import org.example.project.domain.models.EMPTY_GAME_STATE
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.models.toGameState
import org.example.project.domain.useCase.DeleteCurrentGameUseCase
import org.example.project.domain.useCase.GetCurrentGameUseCase
import org.example.project.domain.useCase.MakeAMoveUseCase

data class TicTacToeState(
    internal val id: Long = 0L,
    val gridLength: Int,
    val firstPlayerName: String,
    val secondPlayerName: String,
    val currentPlayer: Int,
    val endedGame: Boolean,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>,
    val currentGridList: List<TicTacToeItem> = currentGrid.values.flatten()
)

class TicTacToeViewModel(
    gameId: Long,
    gridLength: Int,
    firstPlayerName: String,
    secondPlayerName: String,
    private val makeAMoveUseCase: MakeAMoveUseCase,
    private val getCurrentGame: GetCurrentGameUseCase
): ViewModel() {

    private val _state = MutableStateFlow(
        TicTacToeState(
            id = gameId,
            gridLength = gridLength,
            currentPlayer = -1,
            firstPlayerName = firstPlayerName,
            secondPlayerName = secondPlayerName,
            endedGame = false,
            endedGameText = "",
            currentGrid = hashMapOf(
                *List(gridLength) { row ->
                    row to List(gridLength) { TicTacToeItem() }
                }.toTypedArray()
            )
        )
    )
    val state = _state.asStateFlow()

    private var observeGameStateJob: Job? = null

    init {
        observeGameStateJob = viewModelScope.launch(Dispatchers.IO) {
            getCurrentGame(gameId).collect { newState ->
                newState?.let {
                    withContext(Dispatchers.Main) {
                        _state.update { oldState ->
                            oldState.copy(
                                id = newState.id,
                                gridLength = newState.gridLength,
                                firstPlayerName = newState.firstPlayerName,
                                secondPlayerName = newState.secondPlayerName,
                                currentPlayer = newState.currentPlayer,
                                currentGrid = newState.currentGrid,
                                endedGame = newState.endedGame,
                                endedGameText = newState.endedGameText,
                                currentGridList = newState.currentGrid.values
                                    .flatten()
                                    .map { it.copy() }
                            )
                        }
                    }
                }
            }
        }
    }

    fun makeAMove(
        index: Int,
        ticTacToeItem: TicTacToeItem
    ) {
        if (ticTacToeItem.isChecked || _state.value.endedGame) return

        val gameState = _state.value.toGameState()

        viewModelScope.launch(Dispatchers.IO) {
            makeAMoveUseCase(
                index = index,
                currentGameState = gameState
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        observeGameStateJob?.cancel()
        observeGameStateJob = null
    }
}
