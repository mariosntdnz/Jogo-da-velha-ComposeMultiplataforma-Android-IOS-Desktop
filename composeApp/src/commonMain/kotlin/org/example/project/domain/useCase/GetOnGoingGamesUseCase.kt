package org.example.project.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.CurrentGameStateRepository
import org.example.project.domain.models.GameState

class GetOnGoingGamesUseCase(
    private val currentGameStateRepository: CurrentGameStateRepository
) {
    operator fun invoke(): Flow<List<GameState>> {
        return currentGameStateRepository.getAllGamesState().map { list ->
            list.filter { !it.endedGame }
        }
    }
}