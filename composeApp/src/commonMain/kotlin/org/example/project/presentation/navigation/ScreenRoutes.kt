package org.example.project.presentation.navigation

import kotlinx.serialization.Serializable
import org.example.project.domain.models.Player

@Serializable
sealed class Screen(val route: String) {
    @Serializable data object StartGame: Screen("start_game")
    @Serializable data class History(
        val filterType: String
    ): Screen("history_game")
    @Serializable data class TicTacToe(
        val gameId: Long,
        val gridLength: Int
    ): Screen("tic_tac_toe")
}