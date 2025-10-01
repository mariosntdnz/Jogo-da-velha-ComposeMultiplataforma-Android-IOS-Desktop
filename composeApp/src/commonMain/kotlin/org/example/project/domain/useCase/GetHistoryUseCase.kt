package org.example.project.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.allGames.AllGamesRepository
import org.example.project.domain.models.HistoryEntry
import org.example.project.presentation.viewmodels.HistoryEntryType

enum class HistoryFilterType {
    OngoingGames,
    None;
    
    fun getErrorLabel(): String {
        return when(this) {
            OngoingGames -> "Não há jogos em andamento"
            None -> "Não há histórico de jogos"
        }
    }
}

class GetHistoryUseCase(
    private val repository: AllGamesRepository
) {
    operator fun invoke(
        filterType: HistoryFilterType = HistoryFilterType.None
    ): Flow<List<HistoryEntry>> {
        return repository.getAllGamesState().map { allGames ->
            val filteredGames = when(filterType) {
                HistoryFilterType.OngoingGames -> allGames.filter { !it.endedGame }
                HistoryFilterType.None -> allGames
            }
            filteredGames.map {  gameState ->
                HistoryEntry(
                    gameId = gameState.id,
                    historyEntryType = getHistoryTypeFromEndGame(gameState.endedGame),
                    winnerName = "Mockado",
                    currentPlayer = "Mockado",
                    player1Name = gameState.firstPlayerName,
                    player2Name = gameState.secondPlayerName,
                    gridLength = "${gameState.gridLength} x ${gameState.gridLength}"
                )
            }
        }

    }
}

private fun getHistoryTypeFromEndGame(endedGame: Boolean): HistoryEntryType {
    return if (endedGame) {
        HistoryEntryType.Finished
    } else {
        HistoryEntryType.Continue
    }
}