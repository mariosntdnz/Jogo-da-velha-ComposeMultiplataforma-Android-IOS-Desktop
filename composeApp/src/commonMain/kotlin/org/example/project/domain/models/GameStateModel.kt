package org.example.project.domain.models

import org.example.project.data.models.TicTacToeEntity
import org.example.project.presentation.viewmodels.TicTacToeState

data class GameState(
    val id: Long = 0L,
    val gridLength: Int,
    val firstPlayerName: String,
    val secondPlayerName: String,
    val currentPlayer: Int,
    val endedGame: Boolean,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
)

val EMPTY_GAME_STATE = GameState(0,0, "","",-1,false,"", hashMapOf(
    *List(3) { row ->
        row to List(3) { TicTacToeItem(0) }
    }.toTypedArray()
))

fun GameState.toTicTacToeState(): TicTacToeState = TicTacToeState(
    id = this.id,
    gridLength = this.gridLength,
    firstPlayerName = this.firstPlayerName,
    secondPlayerName = this.secondPlayerName,
    currentPlayer = this.currentPlayer,
    endedGame = this.endedGame,
    endedGameText = this.endedGameText,
    currentGrid = HashMap(this.currentGrid.mapValues { entry ->
        entry.value.map { it.copy() }
    })
)

fun TicTacToeState.toGameState(): GameState = GameState(
    id = this.id,
    gridLength = this.gridLength,
    firstPlayerName = this.firstPlayerName,
    secondPlayerName = this.secondPlayerName,
    currentPlayer = this.currentPlayer,
    endedGame = this.endedGame,
    endedGameText = this.endedGameText,
    currentGrid = HashMap(this.currentGrid.mapValues { entry ->
        entry.value.map { it.copy() }
    })
)