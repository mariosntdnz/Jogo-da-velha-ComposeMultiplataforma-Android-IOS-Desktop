package org.example.project.data.dataSource

import kotlinx.coroutines.flow.Flow
import org.example.project.data.models.GameStateEntity

interface TicTacToeLocalDataSource {
    suspend fun updateGame(game: GameStateEntity)
    fun getGameState(id: Int): Flow<GameStateEntity?>
    suspend fun deleteGameState(game: GameStateEntity)
}