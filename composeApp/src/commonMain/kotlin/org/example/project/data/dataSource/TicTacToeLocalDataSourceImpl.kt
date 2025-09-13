package org.example.project.data.dataSource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.dao.GameDao
import org.example.project.data.models.GameStateEntity

class TicTacToeLocalDataSourceImpl(
    private val gameStateDao: GameDao
): TicTacToeLocalDataSource {
    override suspend fun updateGame(game: GameStateEntity) {
        gameStateDao.updateGame(game)
    }

    override fun getGameState(id: Int): Flow<GameStateEntity?> {
        return gameStateDao.getGameState(id)
    }

}