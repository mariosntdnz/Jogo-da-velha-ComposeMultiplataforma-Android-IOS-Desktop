package org.example.project.domain.useCase

import org.example.project.data.repository.currentGame.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class DeleteCurrentGameUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository
) {
    suspend operator fun invoke(gameId: Long) = currentGameStateRepository.deleteGameState(gameId)
}