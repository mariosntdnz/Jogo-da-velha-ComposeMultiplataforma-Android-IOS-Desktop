package org.example.project.di

import org.example.project.presentation.viewmodels.HistoryViewModel
import org.example.project.presentation.viewmodels.StartGameViewModel
import org.example.project.presentation.viewmodels.TicTacToeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::TicTacToeViewModel)
    viewModelOf(::StartGameViewModel)
    viewModelOf(::HistoryViewModel)
}