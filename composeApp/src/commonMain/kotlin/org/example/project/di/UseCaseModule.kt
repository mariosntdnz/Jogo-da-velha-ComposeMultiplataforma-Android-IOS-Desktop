package org.example.project.di

import org.example.project.domain.useCase.MakeAMoveUseCase
import org.example.project.domain.useCase.CheckGameEndUseCase
import org.example.project.domain.useCase.DeleteCurrentGameUseCase
import org.example.project.domain.useCase.GetCurrentGameUseCase
import org.example.project.domain.useCase.GetHistoryUseCase
import org.example.project.domain.useCase.UpsertGameUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory {
        MakeAMoveUseCase(
            upsertGameUseCase = get()
        )
    }
    factory {
        CheckGameEndUseCase()
    }
    factory {
        GetCurrentGameUseCase(
            currentGameStateRepository = get(),
            checkGameEndUseCase = get()
        )
    }

    factory {
        DeleteCurrentGameUseCase(
            currentGameStateRepository = get()
        )
    }

    factory {
        UpsertGameUseCase(
            currentGameStateRepository = get()
        )
    }

    factory {
        GetHistoryUseCase(
            repository = get()
        )
    }
}