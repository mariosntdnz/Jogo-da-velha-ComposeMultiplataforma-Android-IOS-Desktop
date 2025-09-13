package org.example.project.domain.useCase

import org.example.project.data.repository.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class DeleteCurrentGameUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository
) {
    suspend operator fun invoke(game: GameState) = currentGameStateRepository.deleteGameState(game)
}