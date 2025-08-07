package org.example.project.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.ui.tictactoe.TicTacToeState

class TicTacToeViewModel: ViewModel() {
    private val _state = MutableStateFlow(
        TicTacToeState(
            gridLength = 3,
            currentGrid = hashMapOf(
                Pair(0, listOf("X", "", "O")),
                Pair(1, listOf("X", "X", "O")),
                Pair(2, listOf("X", "", ""))
            )
        )
    )
    val state = _state.asStateFlow()
}