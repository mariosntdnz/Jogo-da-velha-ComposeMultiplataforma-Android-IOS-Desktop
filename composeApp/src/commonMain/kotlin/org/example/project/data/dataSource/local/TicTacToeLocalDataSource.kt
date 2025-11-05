package org.example.project.data.dataSource.local

import kotlinx.coroutines.flow.Flow
import org.example.project.data.models.local.GameStateEntity

interface TicTacToeLocalDataSource {
    suspend fun updateGame(game: GameStateEntity): Long
    fun getGameState(id: Long): Flow<GameStateEntity?>
    fun getAllGameState(): Flow<List<GameStateEntity>>
    suspend fun deleteGameState(gameId: Long)
}