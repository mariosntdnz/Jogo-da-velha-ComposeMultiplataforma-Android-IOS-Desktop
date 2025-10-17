package org.example.project.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GameRoom(
    val gameState: GameState,
    val roomId: Long,
    val createRoom: Boolean
)