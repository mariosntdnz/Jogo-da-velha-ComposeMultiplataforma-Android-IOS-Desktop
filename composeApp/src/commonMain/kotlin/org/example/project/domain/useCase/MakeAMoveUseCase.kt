package org.example.project.domain.useCase

import org.example.project.core.listExtensions.copyReplacing
import org.example.project.data.repository.currentGame.UPSERT_ERROR
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem

data class MadeAMoveResult(
    val updated: Boolean = true,
    val error: String = ""
)

class MakeAMoveUseCase(
    private val upsertGameUseCase: UpsertGameUseCase,
    private val checkGameEndUseCase: CheckGameEndUseCase
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
            label = currentPlayer.marker,
            isChecked = true
        )

        currentGrid[row] = currentGrid[row]?.copyReplacing(col, newTicTacToeItem) ?: List(gridLength) { TicTacToeItem() }
        val result = checkGameEndUseCase(currentGameState)
        val newGameState = currentGameState.copy(
            currentGrid = currentGrid,
            currentPlayer = if (!result.gameStateType.isFinished()) {
                if (currentPlayer == currentGameState.firstPlayer) currentGameState.secondPlayer else currentGameState.firstPlayer
            } else {
                currentPlayer
            },
            gameStateType = result.gameStateType,
            endedGameText = result.endGameText

        )

        val updated = upsertGameUseCase(newGameState) != UPSERT_ERROR

        return MadeAMoveResult(
            updated = updated,
            error = if (updated) "" else "Ocorreu um erro inesperado, repita a jogada"
        )
    }
}