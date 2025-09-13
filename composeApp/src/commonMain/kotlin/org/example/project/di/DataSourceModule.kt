package org.example.project.di

import org.example.project.data.dataSource.TicTacToeLocalDataSource
import org.example.project.data.dataSource.TicTacToeLocalDataSourceImpl
import org.example.project.data.database.AppDatabase
import org.koin.dsl.module

val dataSourceModule = module {
    factory<TicTacToeLocalDataSource> {
        TicTacToeLocalDataSourceImpl(
            gameStateDao = get<AppDatabase>().getGameDao()
        )
    }
}