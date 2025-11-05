package org.example.project.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

val modules = listOf(
    viewModelModules,
    useCaseModules,
    dataBasePlatformModule,
    repositoryModule,
    dataSourceModule,
    serviceModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(modules)
}
