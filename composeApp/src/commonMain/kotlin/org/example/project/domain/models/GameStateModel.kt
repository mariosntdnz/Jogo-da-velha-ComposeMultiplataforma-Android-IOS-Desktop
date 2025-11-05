package org.example.project.domain.models

import kotlinx.serialization.Serializable
import org.example.project.domain.useCase.GameStateType
import org.example.project.presentation.viewmodels.TicTacToeState

@Serializable
data class GameState(
    val id: Long = 0L,
    val gridLength: Int,
    val firstPlayer: Player,
    val secondPlayer: Player,
    val currentPlayer: Player,
    val isOnlineGame: Boolean,
    val gameStateType: GameStateType,
    val endedGameText: String,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
)

val EMPTY_GAME_STATE = GameState(0,0, Player(0),Player(1),Player(0), false,GameStateType.Ongoing,"", hashMapOf(
    *List(3) { row ->
        row to List(3) { TicTacToeItem(0) }
    }.toTypedArray()
))

fun GameState.toTicTacToeState(): TicTacToeState = TicTacToeState(
    id = this.id,
    gridLength = this.gridLength,
    firstPlayer = this.firstPlayer,
    secondPlayer = this.secondPlayer,
    currentPlayer = this.currentPlayer,
    gameStateType = this.gameStateType,
    endedGameText = this.endedGameText,
    isOnlineGame = this.isOnlineGame,
    currentGrid = HashMap(this.currentGrid.mapValues { entry ->
        entry.value.map { it.copy() }
    })
)

fun TicTacToeState.toGameState(): GameState = GameState(
    id = this.id,
    gridLength = this.gridLength,
    firstPlayer = this.firstPlayer,
    secondPlayer = this.secondPlayer,
    currentPlayer = this.currentPlayer,
    gameStateType = this.gameStateType,
    endedGameText = this.endedGameText,
    isOnlineGame = this.isOnlineGame,
    currentGrid = HashMap(this.currentGrid.mapValues { entry ->
        entry.value.map { it.copy() }
    })
)