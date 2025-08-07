package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Jogo-da-velha",
    ) {
        App()
    }
}