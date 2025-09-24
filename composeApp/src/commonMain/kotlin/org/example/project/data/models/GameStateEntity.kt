package org.example.project.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.domain.models.TicTacToeItem

@Entity(tableName = "game")
data class GameStateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val player1: String = "",
    val player2: String = "",
    val currentTurn: Int = 0,
    val gridLength: Int = 3,
    val currentGameGrid: HashMap<Int, List<TicTacToeEntity>> = hashMapOf(),
    val endedGame: Boolean = false,
    val endedGameText: String = ""
)