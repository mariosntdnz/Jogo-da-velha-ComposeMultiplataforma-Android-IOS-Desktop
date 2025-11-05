package org.example.project.data.mappers

import org.example.project.data.models.local.GameStateEntity
import org.example.project.data.models.local.TicTacToeEntity
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem
import org.example.project.domain.models.toEntity
import org.example.project.domain.models.toItem
import org.example.project.domain.models.toModel

object GameStateMapper: Mapper<GameState,  GameStateEntity> {
    override fun map(input: GameState): GameStateEntity {

        val mappedGrid: HashMap<Int, List<TicTacToeEntity>> = input.currentGrid.mapValues { entry ->
            entry.value.map { it.toEntity() }
        } as HashMap<Int, List<TicTacToeEntity>>

        return GameStateEntity(
            id = input.id,
            player1 = input.firstPlayer.toEntity(),
            player2 = input.secondPlayer.toEntity(),
            currentTurn = input.currentPlayer.toEntity(),
            currentGameGrid = mappedGrid,
            gameStateType = input.gameStateType,
            endedGameText = input.endedGameText,
            gridLength = input.gridLength
        )
    }

    override fun reverse(output: GameStateEntity): GameState {

        val mappedGrid: HashMap<Int, List<TicTacToeItem>> = output.currentGameGrid.mapValues { entry ->
            entry.value.map { it.toItem() }
        } as HashMap<Int, List<TicTacToeItem>>

        return GameState(
            id = output.id,
            gridLength = output.gridLength,
            firstPlayer = output.player1.toModel(),
            secondPlayer = output.player2.toModel(),
            currentPlayer = output.currentTurn.toModel(),
            gameStateType = output.gameStateType,
            endedGameText = output.endedGameText,
            currentGrid = mappedGrid,
            isOnlineGame = false
        )
    }

}