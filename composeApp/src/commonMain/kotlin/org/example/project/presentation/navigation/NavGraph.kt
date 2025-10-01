package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.presentation.ui.history.HistoryScreen
import org.example.project.presentation.ui.tictactoe.StartGameScreen
import org.example.project.presentation.ui.tictactoe.TicTacToeScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartGame) {
        composable<Screen.StartGame> {
            StartGameScreen(navController = navController)
        }
        composable<Screen.TicTacToe> { screen ->
            val ticTacToe: Screen.TicTacToe = screen.toRoute()
            TicTacToeScreen(
                navController = navController,
                gridLength = ticTacToe.gridLength,
                firstPlayerName = ticTacToe.firstPlayerName,
                secondPlayerName = ticTacToe.secondPlayerName,
                gameId = ticTacToe.gameId
            )
        }
        composable<Screen.History> { screen ->
            val history: Screen.History = screen.toRoute()
            HistoryScreen(
                navController = navController,
                filterType = history.filterType
            )
        }
    }
}