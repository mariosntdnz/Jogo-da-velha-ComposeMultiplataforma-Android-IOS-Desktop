package org.example.project.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable data object StartGame: Screen("start_game")
    @Serializable data object TicTacToe: Screen("tic_tac_toe")
}