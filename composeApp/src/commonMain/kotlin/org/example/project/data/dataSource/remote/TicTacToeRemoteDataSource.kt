package org.example.project.data.dataSource.remote

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameRoom

interface TicTacToeRemoteDataSource {
    suspend fun sendGame(gameState: GameRoom): Long

    fun observeGame(gameId: Long): Flow<GameRoom>
}