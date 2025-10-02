package org.example.project.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.domain.models.Player
import org.example.project.domain.useCase.GameStateType

@Entity(tableName = "game")
data class GameStateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val player1: PlayerEntity,
    val player2: PlayerEntity,
    val currentTurn: PlayerEntity = player1,
    val gridLength: Int = 3,
    val currentGameGrid: HashMap<Int, List<TicTacToeEntity>> = hashMapOf(),
    val gameStateType: GameStateType = GameStateType.Ongoing,
    val endedGameText: String = ""
)