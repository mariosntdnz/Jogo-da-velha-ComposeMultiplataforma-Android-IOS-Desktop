package org.example.project.domain.useCase

import org.example.project.core.const.PLAYER1_MARKER
import org.example.project.core.const.PLAYER2_MARKER
import org.example.project.core.listExtensions.copyReplacing
import org.example.project.data.repository.CurrentGameStateRepository
import org.example.project.data.repository.UPSERT_ERROR
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem

data class MadeAMoveResult(
    val updated: Boolean = true,
    val error: String = ""
)

class MakeAMoveUseCase(
    private val upsertGameUseCase: UpsertGameUseCase
) {
    suspend operator fun invoke(
        index: Int,
        currentGameState: GameState
    ): MadeAMoveResult {

        val gridLength = currentGameState.gridLength
        val currentGrid = currentGameState.currentGrid
        val currentPlayer = currentGameState.currentPlayer

        val row = index / gridLength
        val col = index % gridLength
        val newTicTacToeItem = TicTacToeItem(
            label = if (currentPlayer % 2 == 0) PLAYER1_MARKER else PLAYER2_MARKER,
            isChecked = true
        )

        currentGrid[row] = currentGrid[row]?.copyReplacing(col, newTicTacToeItem) ?: List(gridLength) { TicTacToeItem() }
        val newGameState = currentGameState.copy(
            currentGrid = currentGrid
        )

        val updated = upsertGameUseCase(newGameState) != UPSERT_ERROR

        return MadeAMoveResult(
            updated = updated,
            error = if (updated) "" else "Ocorreu um erro inesperado, repita a jogada"
        )
    }
}