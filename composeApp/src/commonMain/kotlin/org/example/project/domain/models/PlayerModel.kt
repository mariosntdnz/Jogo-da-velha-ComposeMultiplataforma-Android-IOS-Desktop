package org.example.project.domain.models

import kotlinx.serialization.Serializable
import org.example.project.data.models.PlayerEntity

@Serializable
data class Player(
    val id: Long = -1,
    val name: String = "",
    val marker: String = ""
)

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        id = this.id,
        name = this.name,
        marker = this.marker
    )
}

fun PlayerEntity.toModel(): Player {
    return  Player(
        id = this.id,
        name = this.name,
        marker = this.marker
    )
}