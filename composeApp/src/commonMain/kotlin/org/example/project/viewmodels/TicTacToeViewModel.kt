package org.example.project.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.ui.tictactoe.TicTacToeState

data class TicTacToeItem(
    val label: String = "",
    val isChecked: Boolean = false
)

class TicTacToeViewModel: ViewModel() {
    private val _state = MutableStateFlow(
        TicTacToeState(
            gridLength = 3,
            currentGrid = hashMapOf(
                Pair(
                    0,
                    listOf(
                        TicTacToeItem(
                            label = "X",
                            isChecked = true
                        ),
                        TicTacToeItem(),
                        TicTacToeItem(
                            label = "O",
                            isChecked = true
                        )
                    )
                ),
                Pair(
                    1,
                    listOf(
                        TicTacToeItem(
                            label = "X",
                            isChecked = true
                        ),
                        TicTacToeItem(
                            label = "O",
                            isChecked = true
                        ),
                        TicTacToeItem(
                            label = "X",
                            isChecked = true
                        )
                    )
                ),
                Pair(
                    2,
                    listOf(
                        TicTacToeItem(
                            label = "O",
                            isChecked = true
                        ),
                        TicTacToeItem(
                            label = "O",
                            isChecked = true
                        ),
                        TicTacToeItem(
                            label = "O",
                            isChecked = true
                        )
                    )
                )
            )
        )
    )
    val state = _state.asStateFlow()
}