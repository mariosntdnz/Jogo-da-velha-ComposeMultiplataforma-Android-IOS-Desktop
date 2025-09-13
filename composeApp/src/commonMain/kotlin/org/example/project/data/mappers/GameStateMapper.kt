package org.example.project.data.mappers

import org.example.project.data.models.GameStateEntity
import org.example.project.data.models.TicTacToeEntity
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.models.toEntity
import org.example.project.domain.models.toItem

class GameStateMapper: Mapper<GameState,  GameStateEntity> {
    override fun map(input: GameState): GameStateEntity {

        val mappedGrid: HashMap<Int, List<TicTacToeEntity>> = input.currentGrid.mapValues { entry ->
            entry.value.map { it.toEntity() }
        } as HashMap<Int, List<TicTacToeEntity>>

        return GameStateEntity(
            id = 0,
            player1 = input.firstPlayerName,
            player2 = input.secondPlayerName,
            currentTurn = input.currentPlayer,
            currentGameGrid = mappedGrid,
            endedGame = input.endedGame,
            endedGameText = input.endedGameText,
            gridLength = input.gridLength
        )
    }

    override fun reverse(output: GameStateEntity): GameState {

        val mappedGrid: HashMap<Int, List<TicTacToeItem>> = output.currentGameGrid.mapValues { entry ->
            entry.value.map { it.toItem() }
        } as HashMap<Int, List<TicTacToeItem>>

        return GameState(
            gridLength = output.gridLength,
            firstPlayerName = output.player1,
            secondPlayerName = output.player2,
            currentPlayer = output.currentTurn,
            endedGame = output.endedGame,
            endedGameText = output.endedGameText,
            currentGrid = mappedGrid
        )
    }

}