package org.example.project.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.project.data.converters.PlayerConverter
import org.example.project.data.converters.TicTacToeConverter
import org.example.project.data.dao.GameDao
import org.example.project.data.models.local.GameStateEntity
import org.example.project.data.models.local.TicTacToeEntity

@Database(entities = [GameStateEntity::class, TicTacToeEntity::class], version = 1)
@TypeConverters(TicTacToeConverter::class, PlayerConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getGameDao(): GameDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}