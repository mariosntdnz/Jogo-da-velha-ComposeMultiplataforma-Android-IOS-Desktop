package org.example.project.domain.useCase

import org.example.project.core.listExtensions.copyReplacing
import org.example.project.domain.models.TicTacToeItem

data class MadeAMoveResult(
    val grid: HashMap<Int, List<TicTacToeItem>>,
    val currentPlayer: Int
)

class MakeAMoveUseCase() {
    operator fun invoke(
        index: Int,
        gridLength: Int,
        currentGrid: HashMap<Int, List<TicTacToeItem>>,
        currentPlayer: Int
    ): MadeAMoveResult {

        val row = index / gridLength
        val col = index % gridLength
        val newTicTacToeItem = TicTacToeItem(
            label = if (currentPlayer % 2 == 0) "X" else "O",
            isChecked = true
        )

        currentGrid[row] = currentGrid[row]?.copyReplacing(col, newTicTacToeItem) ?: List(gridLength) { TicTacToeItem() }

        return MadeAMoveResult(
            grid = currentGrid,
            currentPlayer = currentPlayer + 1
        )
    }
}