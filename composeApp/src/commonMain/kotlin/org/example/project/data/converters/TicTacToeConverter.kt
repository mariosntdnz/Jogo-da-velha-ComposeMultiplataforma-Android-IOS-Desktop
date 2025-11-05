package org.example.project.data.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.example.project.data.models.local.TicTacToeEntity

class TicTacToeConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromGameGrid(value: HashMap<Int, List<TicTacToeEntity>>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toGameGrid(value: String): HashMap<Int, List<TicTacToeEntity>> {
        return json.decodeFromString(value)
    }
}
