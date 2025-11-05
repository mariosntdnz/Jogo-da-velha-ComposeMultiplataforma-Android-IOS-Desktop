package org.example.project.di

import org.example.project.data.service.TicTacToeWebSocketService
import org.example.project.data.service.TicTacToeWebSocketServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<TicTacToeWebSocketService> {
        TicTacToeWebSocketServiceImpl()
    }
}