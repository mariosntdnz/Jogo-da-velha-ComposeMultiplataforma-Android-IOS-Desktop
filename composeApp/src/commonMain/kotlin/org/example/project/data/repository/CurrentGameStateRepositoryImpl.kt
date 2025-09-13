package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dataSource.TicTacToeLocalDataSource
import org.example.project.data.mappers.Mapper
import org.example.project.data.models.GameStateEntity
import org.example.project.domain.models.GameState

class CurrentGameStateRepositoryImpl(
    private val localDataSource: TicTacToeLocalDataSource,
    private val gameStateMapper: Mapper<GameState, GameStateEntity>
): CurrentGameStateRepository {
    override suspend fun updateGame(game: GameState): Boolean {
        return try {
            localDataSource.updateGame(gameStateMapper.map(game))
            true
        } catch (e: Exception) {
            println("Erro ao realizar update no banco\n $e")
            false
        }
    }

    override fun getGameState(id: Int): Flow<GameState?> {
        return localDataSource.getGameState(id).map {
            it?.let {
                gameStateMapper.reverse(it)
            }
        }
    }

    override suspend fun deleteGameState(game: GameState) {
        localDataSource.deleteGameState(gameStateMapper.map(game))
    }

}