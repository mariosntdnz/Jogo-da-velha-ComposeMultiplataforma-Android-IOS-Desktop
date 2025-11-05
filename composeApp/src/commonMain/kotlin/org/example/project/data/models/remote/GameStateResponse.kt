package org.example.project.data.models.remote

import kotlinx.serialization.Serializable
import org.example.project.domain.models.Player
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.useCase.GameStateType


@Serializable
data class GameStateResponse(
    val id: Long = 0L,
    val gridLength: Int,
    val firstPlayer: Player,
    val secondPlayer: Player,
    val currentPlayer: Player,
    val gameStateType: GameStateType,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
)
