package org.example.project.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable data object StartGame: Screen("start_game")
    @Serializable data class TicTacToe(
        val gridLength: Int,
        val firstPlayerName: String,
        val secondPlayerName: String
    ): Screen("tic_tac_toe")
}