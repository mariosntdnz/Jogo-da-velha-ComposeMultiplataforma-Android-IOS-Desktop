package org.example.project.data.mappers

import org.example.project.data.models.remote.GameStateResponse
import org.example.project.domain.models.GameState
import org.example.project.domain.models.TicTacToeItem

object GameStateRemoteMapper: Mapper<GameState,  GameStateResponse> {
    override fun map(input: GameState): GameStateResponse{

        val mappedGrid: HashMap<Int, List<TicTacToeItem>> = input.currentGrid.mapValues { entry ->
            entry.value.map { it }
        } as HashMap<Int, List<TicTacToeItem>>

        return GameStateResponse(
            id = input.id,
            firstPlayer = input.firstPlayer,
            secondPlayer = input.secondPlayer,
            currentPlayer = input.currentPlayer,
            currentGrid = mappedGrid,
            gameStateType = input.gameStateType,
            endedGameText = input.endedGameText,
            gridLength = input.gridLength
        )
    }

    override fun reverse(output: GameStateResponse): GameState {

        val mappedGrid: HashMap<Int, List<TicTacToeItem>> = output.currentGrid.mapValues { entry ->
            entry.value.map { it}
        } as HashMap<Int, List<TicTacToeItem>>

        return GameState(
            id = output.id,
            gridLength = output.gridLength,
            firstPlayer = output.firstPlayer,
            secondPlayer = output.secondPlayer,
            currentPlayer = output.currentPlayer,
            gameStateType = output.gameStateType,
            endedGameText = output.endedGameText,
            currentGrid = mappedGrid,
            isOnlineGame = true
        )
    }

}