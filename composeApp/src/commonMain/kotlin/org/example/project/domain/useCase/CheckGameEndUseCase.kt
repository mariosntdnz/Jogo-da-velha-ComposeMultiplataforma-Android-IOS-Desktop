package org.example.project.domain.useCase

import org.example.project.domain.models.TicTacToeItem

data class EndGameResult(
    val endGame: Boolean,
    val endGameText: String
)

class CheckGameEndUseCase() {
    operator fun invoke(
        gridLength: Int,
        currentGrid: HashMap<Int, List<TicTacToeItem>>,
        gridGeneralList: List<TicTacToeItem>,
        currentPlayer: Int
    ): EndGameResult {

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
        val endedGameText = when {
            hasWinner -> "Player ${(currentPlayer % 2 ) + 1} Ganhou !!"
            itsDraw -> "Deu velha !!"
            else -> ""
        }

        return EndGameResult(
            endGame = hasWinner || itsDraw,
            endGameText = endedGameText
        )
    }
}