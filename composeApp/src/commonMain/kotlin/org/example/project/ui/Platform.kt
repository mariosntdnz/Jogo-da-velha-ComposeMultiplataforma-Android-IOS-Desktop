package org.example.project.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform