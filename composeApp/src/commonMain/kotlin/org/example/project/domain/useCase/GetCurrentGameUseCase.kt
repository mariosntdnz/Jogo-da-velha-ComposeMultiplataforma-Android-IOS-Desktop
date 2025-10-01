package org.example.project.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.currentGame.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class GetCurrentGameUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository,
    private val checkGameEndUseCase: CheckGameEndUseCase
) {
    operator fun invoke(id: Long): Flow<GameState?> {
        return currentGameStateRepository.getGameState(id).map { gameResult ->
            gameResult?.let { game ->
                val result = checkGameEndUseCase(game)
                val endGame = result.endGame
                val currentPlayer = game.currentPlayer
                game.copy(
                    endedGame = endGame,
                    endedGameText = result.endGameText,
                    currentPlayer = if (endGame) currentPlayer else game.currentPlayer + 1
                )
            }
        }
    }
}