package org.example.project.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.currentGame.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class GetCurrentGameUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository
) {
    operator fun invoke(id: Long): Flow<GameState?> {
        return currentGameStateRepository.getGameState(id)
    }
}