package org.example.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.useCase.CheckGameEndUseCase
import org.example.project.domain.useCase.MakeAMoveUseCase

data class TicTacToeState(
    val gridLength: Int,
    val firstPlayerName: String,
    val secondPlayerName: String,
    val currentPlayer: Int,
    val endedGame: Boolean,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
) {
    val currentGridList get() = currentGrid.values.flatten()
}

class TicTacToeViewModel(
    gridLength: Int,
    firstPlayerName: String,
    secondPlayerName: String,
    private val makeAMoveUseCase: MakeAMoveUseCase,
    private val checkGameEndUseCase: CheckGameEndUseCase
): ViewModel() {
    private val _state = MutableStateFlow(
        TicTacToeState(
            gridLength = gridLength,
            currentPlayer = 0,
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

    fun makeAMove(
        index: Int,
        ticTacToeItem: TicTacToeItem
    ) {
        if (ticTacToeItem.isChecked || _state.value.endedGame) return

        val currentPlayer = _state.value.currentPlayer

        _state.update { oldState ->

            val result = makeAMoveUseCase(
                index = index,
                gridLength = oldState.gridLength,
                currentGrid = oldState.currentGrid,
                currentPlayer = oldState.currentPlayer
            )

            oldState.copy(
                currentGrid = result.grid,
                currentPlayer = result.currentPlayer
            )
        }

        verifyIfFinishGame(currentPlayer)

    }

    private fun verifyIfFinishGame(currentPlayer: Int) {
        _state.update { oldState ->
            val result = checkGameEndUseCase(
                gridLength = oldState.gridLength,
                firstPlayerName = oldState.firstPlayerName,
                secondPlayerName = oldState.secondPlayerName,
                currentPlayer = currentPlayer,
                currentGrid = oldState.currentGrid,
                gridGeneralList = oldState.currentGridList
            )

            oldState.copy(
                endedGame = result.endGame,
                endedGameText = result.endGameText
            )
        }
    }

}
