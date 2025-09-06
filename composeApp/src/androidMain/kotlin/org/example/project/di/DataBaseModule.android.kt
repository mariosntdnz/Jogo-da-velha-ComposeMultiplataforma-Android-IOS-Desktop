package org.example.project.di

import org.example.project.data.database.AppDatabase
import org.example.project.data.database.getDatabaseBuilder
import org.example.project.data.database.getRoomDatabase
import org.koin.dsl.module

actual val dataBasePlatformModule = module {
    single<AppDatabase> {
        getRoomDatabase(getDatabaseBuilder(get()))
    }
}