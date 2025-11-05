package org.example.project.di

import org.example.project.data.dataSource.local.TicTacToeLocalDataSource
import org.example.project.data.dataSource.local.TicTacToeLocalDataSourceImpl
import org.example.project.data.dataSource.remote.TicTacToeRemoteDataSource
import org.example.project.data.dataSource.remote.TicTacToeRemoteDataSourceImpl
import org.example.project.data.database.AppDatabase
import org.koin.dsl.module

val dataSourceModule = module {
    factory<TicTacToeLocalDataSource> {
        TicTacToeLocalDataSourceImpl(
            gameStateDao = get<AppDatabase>().getGameDao()
        )
    }
    factory<TicTacToeRemoteDataSource> {
        TicTacToeRemoteDataSourceImpl(
            api = get()
        )
    }
}