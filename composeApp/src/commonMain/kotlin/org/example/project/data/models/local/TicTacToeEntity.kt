package org.example.project.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class TicTacToeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val label: String = "",
    val isChecked: Boolean = false
)