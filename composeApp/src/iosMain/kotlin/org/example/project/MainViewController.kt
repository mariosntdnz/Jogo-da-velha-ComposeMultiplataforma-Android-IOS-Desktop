package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.di.initKoin

fun doInitKoinIos() = initKoin()

fun MainViewController() = ComposeUIViewController { App() }