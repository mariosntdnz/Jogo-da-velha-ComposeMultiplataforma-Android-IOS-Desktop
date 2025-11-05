package org.example.project.data.repository.currentGame

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dataSource.local.TicTacToeLocalDataSource
import org.example.project.data.dataSource.remote.TicTacToeRemoteDataSource
import org.example.project.data.mappers.Mapper
import org.example.project.data.models.local.GameStateEntity
import org.example.project.data.models.remote.GameStateResponse
import org.example.project.domain.models.GameRoom
import org.example.project.domain.models.GameState

const val UPSERT_ERROR = -1L
const val DEFAULT_ROOM = 1L

class CurrentGameStateRepositoryImpl(
    private val localDataSource: TicTacToeLocalDataSource,
    private val remoteDataSource: TicTacToeRemoteDataSource,
    private val gameStateLocalMapper: Mapper<GameState, GameStateEntity>,
    private val gameStateRemoteMapper: Mapper<GameState, GameStateResponse>
): CurrentGameStateRepository {
    override suspend fun updateGame(game: GameState): Long {
        return try {
            if (game.isOnlineGame) {
                remoteDataSource.sendGame(GameRoom(gameState = gameStateRemoteMapper.map(game), roomId = DEFAULT_ROOM, createRoom = false))
            } else {
                localDataSource.updateGame(gameStateLocalMapper.map(game))
            }
        } catch (e: Exception) {
            println("Erro ao realizar update - gameOnline = ${game.isOnlineGame}\n $e")
            UPSERT_ERROR
        }
    }

    override fun getGameState(
        isOnlineGame: Boolean,
        id: Long
    ): Flow<GameState?> {
        return if (isOnlineGame) {
            remoteDataSource.observeGame(id).map { gameStateRemoteMapper.reverse(it.gameState)}
        } else {
            localDataSource.getGameState(id).map { it?.let { gameStateLocalMapper.reverse(it) } }
        }

    }

    override suspend fun deleteGameState(gameId: Long) {
        localDataSource.deleteGameState(gameId)
    }

}