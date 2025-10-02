package org.example.project.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val marker: String
)