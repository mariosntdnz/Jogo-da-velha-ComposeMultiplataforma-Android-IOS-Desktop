package org.example.project.data.dataSource.remote

import kotlinx.coroutines.flow.Flow
import org.example.project.data.service.TicTacToeWebSocketService
import org.example.project.domain.models.GameRoom

class TicTacToeRemoteDataSourceImpl(
    private val api: TicTacToeWebSocketService
): TicTacToeRemoteDataSource {
    override suspend fun sendGame(gameState: GameRoom): Long = api.sendMsg(msg = gameState)


    override fun observeGame(gameId: Long): Flow<GameRoom> = api.onMsgReceived()
}