package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.di.dataBasePlatformModule
import org.example.project.di.useCaseModules
import org.example.project.di.viewModelModules
import org.example.project.presentation.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

fun koinConfiguration(): KoinApplication.() -> Unit = {
    modules(viewModelModules, useCaseModules, dataBasePlatformModule)
}

@Composable
@Preview
fun App() {
    KoinApplication(application = koinConfiguration()) {
        MaterialTheme {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                backgroundColor = Color.White
            ) {
                AppNavigation()
            }
        }
    }
}