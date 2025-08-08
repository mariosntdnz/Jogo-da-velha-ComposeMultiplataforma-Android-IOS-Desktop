package org.example.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TicTacToeItem(
    val label: String = "",
    val isChecked: Boolean = false
)

data class TicTacToeState(
    val gridLength: Int,
    val currentPlayer: Int,
    val finishGame: Boolean,
    val finishGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
) {
    val currentGridList get() = currentGrid.values.flatten()
}

class TicTacToeViewModel: ViewModel() {
    private val _state = MutableStateFlow(
        TicTacToeState(
            gridLength = 3,
            currentPlayer = 0,
            finishGame = false,
            finishGameText = "",
            currentGrid = hashMapOf(
                *List(3) { row ->
                    row to List(3) { TicTacToeItem() }
                }.toTypedArray()
            )
        )
    )
    val state = _state.asStateFlow()

    fun play(
        index: Int,
        ticTacToeItem: TicTacToeItem
    ) {
        if (ticTacToeItem.isChecked || _state.value.finishGame) return

        _state.update { oldState ->

            val gridLength = oldState.gridLength
            val currentGrid = oldState.currentGrid
            val row = index / gridLength
            val col = index % gridLength
            val currentPlayer = oldState.currentPlayer
            val newTicTacToeItem = TicTacToeItem(
                label = if (currentPlayer % 2 == 0) "X" else "O",
                isChecked = true
            )
            currentGrid[row] = currentGrid[row]?.copyReplacing(col, newTicTacToeItem) ?: List(gridLength) { TicTacToeItem() }

            oldState.copy(
                currentGrid = currentGrid,
                currentPlayer = currentPlayer + 1
            )
        }

        verifyIfFinishGame()

    }

    private fun verifyIfFinishGame() {
        _state.update { oldState ->

            val currentGrid = oldState.currentGrid
            val gridLength = oldState.gridLength
            val gridGeneralList = oldState.currentGridList

            val horizontallyResult = currentGrid.keys.any { key ->
                val row = currentGrid[key]
                val first = row?.firstOrNull()
                first?.label?.isNotEmpty() == true  && row.distinct().size == 1
            }

            val verticallyResult = (0 until gridLength).any { col ->
                val first = gridGeneralList.getOrNull(col)
                first?.label?.isNotEmpty() == true && (1 until gridLength).all { row ->
                    gridGeneralList.getOrNull(row * gridLength + col) == first
                }
            }

            val diagonalResult = run {
                val firstMain = gridGeneralList.getOrNull(0)
                val mainEqual = firstMain?.label?.isNotEmpty() == true && (1 until gridLength).all { i ->
                    gridGeneralList.getOrNull(i * (gridLength + 1)) == firstMain
                }

                val firstSecondary = gridGeneralList.getOrNull(gridLength - 1)
                val secondaryEqual = firstSecondary?.label?.isNotEmpty() == true && (1 until gridLength).all { i ->
                    gridGeneralList.getOrNull(i * (gridLength - 1) + (gridLength - 1)) == firstSecondary
                }

                mainEqual || secondaryEqual
            }

            val hasWinner = horizontallyResult || verticallyResult || diagonalResult
            val itsDraw = !hasWinner && gridGeneralList.none { it.label.isEmpty() }
            val finishGameText = when {
                hasWinner -> "Player ${((oldState.currentPlayer % 2 ) xor 1) + 1} Ganhou !!"
                itsDraw -> "Deu velha !!"
                else -> ""
            }

            oldState.copy(
                finishGame = hasWinner || itsDraw,
                finishGameText = finishGameText
            )
        }
    }

}

fun <T> List<T>.copyReplacing(index: Int, newItem: T): List<T> {
    if (index !in indices) return this
    return mapIndexed { i, item ->
        if (i == index) newItem else item
    }
}