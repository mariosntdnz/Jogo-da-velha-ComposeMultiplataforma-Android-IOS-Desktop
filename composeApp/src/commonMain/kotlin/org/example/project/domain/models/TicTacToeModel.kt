package org.example.project.domain.models

import org.example.project.data.models.TicTacToeEntity

data class TicTacToeItem(
    val id: Int = 0,
    val label: String = "",
    val isChecked: Boolean = false
)

fun TicTacToeEntity.toItem(): TicTacToeItem {
    return TicTacToeItem(
        id = this.id,
        label = this.label,
        isChecked = this.isChecked
    )
}

fun TicTacToeItem.toEntity(): TicTacToeEntity {
    return TicTacToeEntity(
        id = this.id,
        label = this.label,
        isChecked = this.isChecked
    )
}
