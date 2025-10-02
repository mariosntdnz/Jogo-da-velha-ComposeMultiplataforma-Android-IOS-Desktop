package org.example.project.domain.models

import org.example.project.presentation.viewmodels.HistoryEntryType

data class HistoryEntry(
    val gameId: Long,
    val historyEntryType: HistoryEntryType,
    val winnerName: String,
    val currentPlayer: Player,
    val player1: Player,
    val player2: Player,
    val gridLength: Int,
    val gridLengthLabel: String,
    val gameHistoryTitle: String
)