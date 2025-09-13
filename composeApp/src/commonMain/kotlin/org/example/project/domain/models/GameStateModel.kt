package org.example.project.domain.models

import org.example.project.presentation.viewmodels.TicTacToeState

data class GameState(
    val gridLength: Int,
    val firstPlayerName: String,
    val secondPlayerName: String,
    val currentPlayer: Int,
    val endedGame: Boolean,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
)

val EMPTY_GAME_STATE = GameState(0, "","",0,false,"", hashMapOf())

fun GameState.toTicTacToeState(): TicTacToeState = TicTacToeState(
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