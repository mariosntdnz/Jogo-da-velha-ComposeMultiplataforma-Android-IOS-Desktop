package org.example.project.core.listExtensions

fun <T> List<T>.copyReplacing(index: Int, newItem: T): List<T> {
    if (index !in indices) return this
    return mapIndexed { i, item ->
        if (i == index) newItem else item
    }
}