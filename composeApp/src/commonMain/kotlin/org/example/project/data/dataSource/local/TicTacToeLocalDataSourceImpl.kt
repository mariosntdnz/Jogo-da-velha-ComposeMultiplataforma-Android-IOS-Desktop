package org.example.project.data.dataSource.local

import kotlinx.coroutines.flow.Flow
import org.example.project.data.dao.GameDao
import org.example.project.data.models.local.GameStateEntity

class TicTacToeLocalDataSourceImpl(
    private val gameStateDao: GameDao
): TicTacToeLocalDataSource {
    override suspend fun updateGame(game: GameStateEntity): Long {
        return gameStateDao.updateGame(game)
    }

    override fun getGameState(id: Long): Flow<GameStateEntity?> {
        return gameStateDao.getGameState(id)
    }

    override fun getAllGameState(): Flow<List<GameStateEntity>> {
        return gameStateDao.getAllGameState()
    }

    override suspend fun deleteGameState(gameId: Long) {
        gameStateDao.deleteGame(gameId)
    }

}