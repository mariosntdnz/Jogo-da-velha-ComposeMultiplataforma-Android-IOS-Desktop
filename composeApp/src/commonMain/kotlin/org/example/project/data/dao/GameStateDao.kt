package org.example.project.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.project.data.models.GameStateEntity

@Dao
interface GameDao {

    @Upsert
    suspend fun updateGame(game: GameStateEntity): Long

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun deleteGame(id: Long)

    @Query("select * from game where id = :id")
    fun getGameState(id: Long): Flow<GameStateEntity?>

    @Query("select * from game")
    fun getAllGameState(): Flow<List<GameStateEntity>>
}