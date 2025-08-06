package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.tictactoe.StartGameScreen
import org.example.project.tictactoe.TicTacToeScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartGame) {
        composable<Screen.StartGame> {
            StartGameScreen(navController = navController)
        }
        composable<Screen.TicTacToe> {
            TicTacToeScreen(navController = navController)
        }
    }
}