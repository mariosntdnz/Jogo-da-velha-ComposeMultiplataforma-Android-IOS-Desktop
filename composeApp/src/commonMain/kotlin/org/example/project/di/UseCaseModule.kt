package org.example.project.di

import org.example.project.domain.useCase.MakeAMoveUseCase
import org.example.project.domain.useCase.CheckGameEndUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory {
        MakeAMoveUseCase()
    }
    factory {
        CheckGameEndUseCase()
    }
}