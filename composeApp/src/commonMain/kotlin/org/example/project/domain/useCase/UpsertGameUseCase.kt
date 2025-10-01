package org.example.project.domain.useCase

import org.example.project.data.repository.currentGame.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class UpsertGameUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository
) {
    suspend operator fun invoke(game: GameState) = currentGameStateRepository.updateGame(game)
}