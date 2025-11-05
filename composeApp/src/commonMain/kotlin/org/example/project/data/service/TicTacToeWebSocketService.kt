package org.example.project.data.service

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameRoom

interface TicTacToeWebSocketService {
    suspend fun sendMsg(msg: GameRoom): Long

    suspend fun connect()

    suspend fun disconnect()

    fun onMsgReceived(): Flow<GameRoom>

}