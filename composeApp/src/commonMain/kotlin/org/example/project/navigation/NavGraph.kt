package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.tictactoe.StartGameScreen
import org.example.project.ui.tictactoe.TicTacToeScreen
import org.example.project.ui.tictactoe.TicTacToeState


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartGame) {
        composable<Screen.StartGame> {
            StartGameScreen(navController = navController)
        }
        composable<Screen.TicTacToe> {
            TicTacToeScreen(
                navController = navController,
                state = TicTacToeState(
                    gridLength = 3,
                    currentGrid = hashMapOf(
                        Pair(0, listOf("X","","O")),
                        Pair(1, listOf("X","X","O")),
                        Pair(2, listOf("X","",""))
                    )
                )
            )
        }
    }
}