package org.example.project.data.mappers

interface Mapper<I,O> {
    fun map(input: I): O
    fun reverse(output: O): I
}

fun <I, O> Mapper<I, O>.mapList(list: List<I>): List<O> = list.map { map(it) }