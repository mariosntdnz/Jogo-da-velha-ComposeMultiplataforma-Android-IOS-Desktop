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
    suspend fun updateGame(game: GameStateEntity)

    @Delete
    suspend fun deleteGame(game: GameStateEntity)

    @Query("select * from game where id = :id")
    fun getGameState(id: Int): Flow<GameStateEntity?>
}