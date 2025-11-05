package org.example.project.data.repository.currentGame

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dataSource.local.TicTacToeLocalDataSource
import org.example.project.data.dataSource.remote.TicTacToeRemoteDataSource
import org.example.project.data.mappers.Mapper
import org.example.project.data.models.GameStateEntity
import org.example.project.domain.models.GameRoom
import org.example.project.domain.models.GameState

const val UPSERT_ERROR = -1L
const val DEFAULT_ROOM = 1L

class CurrentGameStateRepositoryImpl(
    private val localDataSource: TicTacToeLocalDataSource,
    private val remoteDataSource: TicTacToeRemoteDataSource,
    private val gameStateMapper: Mapper<GameState, GameStateEntity>
): CurrentGameStateRepository {
    override suspend fun updateGame(game: GameState): Long {
        return try {
            if (game.isOnlineGame) {
                remoteDataSource.sendGame(GameRoom(gameState = game, roomId = DEFAULT_ROOM, createRoom = false))
            } else {
                localDataSource.updateGame(gameStateMapper.map(game))
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
            remoteDataSource.observeGame(id).map { it.gameState }
        } else {
            localDataSource.getGameState(id).map { it?.let { gameStateMapper.reverse(it) } }
        }

    }

    override suspend fun deleteGameState(gameId: Long) {
        localDataSource.deleteGameState(gameId)
    }

}