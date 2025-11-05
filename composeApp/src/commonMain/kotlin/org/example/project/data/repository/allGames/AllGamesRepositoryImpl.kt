package org.example.project.data.repository.allGames

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dataSource.local.TicTacToeLocalDataSource
import org.example.project.data.mappers.Mapper
import org.example.project.data.models.local.GameStateEntity
import org.example.project.domain.models.GameState

class AllGamesRepositoryImpl(
    private val ticTacToeLocalDataSource: TicTacToeLocalDataSource,
    private val gameStateMapper: Mapper<GameState, GameStateEntity>
): AllGamesRepository {
    override fun getAllGamesState(): Flow<List<GameState>> {
        return ticTacToeLocalDataSource.getAllGameState().map { list ->
            list.map(gameStateMapper::reverse)
        }
    }
}