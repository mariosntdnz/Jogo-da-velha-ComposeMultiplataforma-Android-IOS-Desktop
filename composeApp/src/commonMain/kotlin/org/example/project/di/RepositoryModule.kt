package org.example.project.di

import org.example.project.data.mappers.GameStateMapper
import org.example.project.data.repository.allGames.AllGamesRepository
import org.example.project.data.repository.allGames.AllGamesRepositoryImpl
import org.example.project.data.repository.currentGame.CurrentGameStateRepository
import org.example.project.data.repository.currentGame.CurrentGameStateRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<CurrentGameStateRepository> {
        CurrentGameStateRepositoryImpl(
            localDataSource = get(),
            gameStateMapper = GameStateMapper,
            remoteDataSource = get()
        )
    }

    factory<AllGamesRepository> {
        AllGamesRepositoryImpl(
            ticTacToeLocalDataSource = get(),
            gameStateMapper = GameStateMapper
        )
    }
}