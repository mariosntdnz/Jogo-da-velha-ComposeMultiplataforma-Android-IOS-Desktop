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
import org.example.project.domain.models.Player
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.models.toGameState
import org.example.project.domain.useCase.GameStateType
import org.example.project.domain.useCase.GetCurrentGameUseCase
import org.example.project.domain.useCase.MakeAMoveUseCase
import org.example.project.domain.useCase.UpsertGameUseCase

data class TicTacToeState(
    internal val id: Long = 0L,
    val gridLength: Int,
    val firstPlayer: Player,
    val secondPlayer: Player,
    val currentPlayer: Player,
    val isOnlineGame: Boolean,
    val gameStateType: GameStateType,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>,
    val currentGridList: List<TicTacToeItem> = currentGrid.values.flatten()
)

class TicTacToeViewModel(
    gameId: Long,
    gridLength: Int,
    isOnlineGame: Boolean,
    private val makeAMoveUseCase: MakeAMoveUseCase,
    private val getCurrentGame: GetCurrentGameUseCase,
    private val upsertGameUseCase: UpsertGameUseCase
): ViewModel() {

    private val _state = MutableStateFlow(
        TicTacToeState(
            id = gameId,
            gridLength = gridLength,
            currentPlayer = Player(),
            firstPlayer = Player(),
            secondPlayer = Player(),
            gameStateType = GameStateType.Ongoing,
            endedGameText = "",
            isOnlineGame = isOnlineGame,
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
            getCurrentGame(gameId, isOnlineGame).collect { newState ->
                newState?.let {
                    withContext(Dispatchers.Main) {
                        _state.update { oldState ->
                            oldState.copy(
                                id = newState.id,
                                isOnlineGame = oldState.isOnlineGame,
                                gridLength = newState.gridLength,
                                firstPlayer = newState.firstPlayer,
                                secondPlayer = newState.secondPlayer,
                                currentPlayer = newState.currentPlayer,
                                currentGrid = newState.currentGrid,
                                gameStateType = newState.gameStateType,
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
        if (ticTacToeItem.isChecked || _state.value.gameStateType.isFinished()) return

        val gameState = _state.value.toGameState()

        viewModelScope.launch(Dispatchers.IO) {
            makeAMoveUseCase(
                index = index,
                currentGameState = gameState
            )
        }
    }

    fun onFinishGame(
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            upsertGameUseCase(_state.value.toGameState())
            onDone()
        }
    }

    override fun onCleared() {
        super.onCleared()
        observeGameStateJob?.cancel()
        observeGameStateJob = null
    }
}
