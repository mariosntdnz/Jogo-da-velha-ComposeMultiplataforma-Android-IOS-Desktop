package org.example.project.data.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.example.project.data.models.local.PlayerEntity

class PlayerConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromPlayer(value: PlayerEntity): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toPlayer(value: String): PlayerEntity {
        return json.decodeFromString(value)
    }
}