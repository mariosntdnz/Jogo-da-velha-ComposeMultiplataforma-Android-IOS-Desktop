package org.example.project.domain.models

import kotlinx.serialization.Serializable
import org.example.project.data.models.remote.GameStateResponse

@Serializable
data class GameRoom(
    val gameState: GameStateResponse,
    val roomId: Long,
    val createRoom: Boolean
)