package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameState

interface CurrentGameStateRepository {
    suspend fun updateGame(game: GameState): Long
    fun getGameState(id: Long): Flow<GameState?>
    fun getAllGamesState(): Flow<List<GameState>>
    suspend fun deleteGameState(game: GameState)
}