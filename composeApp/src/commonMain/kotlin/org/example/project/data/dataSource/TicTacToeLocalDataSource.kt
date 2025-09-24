package org.example.project.data.dataSource

import kotlinx.coroutines.flow.Flow
import org.example.project.data.models.GameStateEntity

interface TicTacToeLocalDataSource {
    suspend fun updateGame(game: GameStateEntity): Long
    fun getGameState(id: Long): Flow<GameStateEntity?>
    fun getAllGameState(): Flow<List<GameStateEntity>?>
    suspend fun deleteGameState(game: GameStateEntity)
}