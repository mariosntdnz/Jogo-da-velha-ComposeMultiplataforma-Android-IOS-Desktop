package org.example.project.data.repository.allGames

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GameState

interface AllGamesRepository {
    fun getAllGamesState(): Flow<List<GameState>>
}