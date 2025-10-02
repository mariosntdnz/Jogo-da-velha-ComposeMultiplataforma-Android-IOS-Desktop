package org.example.project.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.core.const.DRAW_LABEL
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem

enum class GameStateType {
    Winner,
    Ongoing,
    Draw;

    fun isFinished() = this == Winner || this == Draw
}

data class GameStateResult(
    val gameStateType: GameStateType,
    val endGameText: String
)

class CheckGameEndUseCase {
    suspend operator fun invoke(
        gameState: GameState
    ): GameStateResult = withContext(Dispatchers.Default){

        val gridLength = gameState.gridLength
        val currentGrid = gameState.currentGrid
        val gridGeneralList = currentGrid.values.flatten()
        val currentPlayer = gameState.currentPlayer

        val hasWinner = checkHorizontalWin(currentGrid) || checkVerticalWin(gridGeneralList, gridLength) || checkDiagonalWin(gridGeneralList, gridLength)

        val itsDraw = !hasWinner && gridGeneralList.none { it.label.isEmpty() }

        val endedGameText = when {
            hasWinner -> "${currentPlayer.name} Ganhou !!"
            itsDraw -> DRAW_LABEL
            else -> ""
        }

        return@withContext GameStateResult(
            gameStateType = when {
                hasWinner -> GameStateType.Winner
                itsDraw -> GameStateType.Draw
                else -> GameStateType.Ongoing
            },
            endGameText = endedGameText
        )
    }
}

private fun checkHorizontalWin(currentGrid: Map<Int, List<TicTacToeItem>>): Boolean {
    return currentGrid.keys.any { key ->
        val row = currentGrid[key]
        val first = row?.firstOrNull()
        first?.label?.isNotEmpty() == true && row.distinct().size == 1
    }
}

private fun checkVerticalWin(grid: List<TicTacToeItem>, gridLength: Int): Boolean {
    return (0 until gridLength).any { col ->
        val first = grid.getOrNull(col)
        first?.label?.isNotEmpty() == true && (1 until gridLength).all { row ->
            grid.getOrNull(row * gridLength + col) == first
        }
    }
}

private fun checkDiagonalWin(grid: List<TicTacToeItem>, gridLength: Int): Boolean {
    val firstMain = grid.getOrNull(0)
    val mainEqual = firstMain?.label?.isNotEmpty() == true && (1 until gridLength).all { i ->
        grid.getOrNull(i * (gridLength + 1)) == firstMain
    }

    val firstSecondary = grid.getOrNull(gridLength - 1)
    val secondaryEqual = firstSecondary?.label?.isNotEmpty() == true && (1 until gridLength).all { i ->
        grid.getOrNull(i * (gridLength - 1) + (gridLength - 1)) == firstSecondary
    }

    return mainEqual || secondaryEqual
}
