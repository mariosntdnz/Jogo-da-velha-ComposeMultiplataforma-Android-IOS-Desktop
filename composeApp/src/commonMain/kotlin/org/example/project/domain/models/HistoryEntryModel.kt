package org.example.project.domain.models

import org.example.project.presentation.viewmodels.HistoryEntryType

data class HistoryEntry(
    val gameId: Long,
    val historyEntryType: HistoryEntryType,
    val winnerName: String,
    val currentPlayer: String,
    val player1Name: String,
    val player2Name: String,
    val gridLength: String
) {
    val gameHistoryTitle get() = "$player1Name x $player2Name"
}