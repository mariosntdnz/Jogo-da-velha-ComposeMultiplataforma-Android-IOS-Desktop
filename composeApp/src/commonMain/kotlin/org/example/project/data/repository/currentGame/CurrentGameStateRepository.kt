package org.example.project.data.repository.currentGame

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameState

interface CurrentGameStateRepository {
    suspend fun updateGame(game: GameState): Long
    fun getGameState(id: Long): Flow<GameState?>
    suspend fun deleteGameState(game: GameState)
}