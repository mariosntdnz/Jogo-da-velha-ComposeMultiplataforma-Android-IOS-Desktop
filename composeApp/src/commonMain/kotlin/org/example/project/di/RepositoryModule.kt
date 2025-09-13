package org.example.project.di

import org.example.project.data.mappers.GameStateMapper
import org.example.project.data.repository.CurrentGameStateRepository
import org.example.project.data.repository.CurrentGameStateRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<CurrentGameStateRepository> {
        CurrentGameStateRepositoryImpl(
            localDataSource = get(),
            gameStateMapper = GameStateMapper()
        )
    }
}