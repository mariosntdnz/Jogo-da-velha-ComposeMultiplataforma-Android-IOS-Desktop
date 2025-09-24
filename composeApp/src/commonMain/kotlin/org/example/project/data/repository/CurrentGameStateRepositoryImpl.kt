package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dataSource.TicTacToeLocalDataSource
import org.example.project.data.mappers.Mapper
import org.example.project.data.models.GameStateEntity
import org.example.project.domain.models.GameState

const val UPSERT_ERROR = -1L

class CurrentGameStateRepositoryImpl(
    private val localDataSource: TicTacToeLocalDataSource,
    private val gameStateMapper: Mapper<GameState, GameStateEntity>
): CurrentGameStateRepository {
    override suspend fun updateGame(game: GameState): Long {
        return try {
            localDataSource.updateGame(gameStateMapper.map(game))
        } catch (e: Exception) {
            println("Erro ao realizar update no banco\n $e")
            UPSERT_ERROR
        }
    }

    override fun getGameState(id: Long): Flow<GameState?> {
        return localDataSource.getGameState(id).map {
            it?.let {
                gameStateMapper.reverse(it)
            }
        }
    }

    override fun getAllGamesState(): Flow<List<GameState>> {
        return localDataSource.getAllGameState().map { list ->
            list?.map { gameStateMapper.reverse(it) } ?: emptyList()
        }
    }

    override suspend fun deleteGameState(game: GameState) {
        localDataSource.deleteGameState(gameStateMapper.map(game))
    }

}