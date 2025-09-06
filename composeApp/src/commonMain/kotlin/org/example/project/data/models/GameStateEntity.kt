package org.example.project.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameStateEntity(
    @PrimaryKey
    val id: Int = 0,
    val player1: String = "",
    val player2: String = "",
    val currentTurn: Int = 0,
    val currentGameGrid: HashMap<Int, List<TicTacToeEntity>> = hashMapOf(),
    val endedGame: Boolean = false
)