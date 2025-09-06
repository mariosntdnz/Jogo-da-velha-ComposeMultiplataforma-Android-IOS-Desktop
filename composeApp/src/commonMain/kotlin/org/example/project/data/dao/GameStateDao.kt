package org.example.project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.example.project.data.models.GameStateEntity

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameStateEntity)

    @Update
    suspend fun updateGame(game: GameStateEntity)

    @Query("select * from game where id = 0")
    fun getGameState(): Flow<GameStateEntity>
}