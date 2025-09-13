package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameState

interface CurrentGameStateRepository {
    suspend fun updateGame(game: GameState): Boolean
    fun getGameState(id: Int): Flow<GameState?>
}