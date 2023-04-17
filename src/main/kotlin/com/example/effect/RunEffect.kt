package com.example.effect

import arrow.core.continuations.EagerEffect
import arrow.core.continuations.Effect
import arrow.core.continuations.eagerEffect
import arrow.core.continuations.effect
import arrow.core.continuations.ensureNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(): Unit = runBlocking {
    val i = -1
    val plusOne: Effect<String, Int> = effect {
        println("1")
//        if (i <= 0)
//            shift<Int>("$i is not a positive value")
        ensure(i > 0) { "$i is not a positive value" }
        println("2")
        i + 1
    }
    println("3")
    plusOne.orNull()
}

/**
 *   console print out:
 *   3
 *   1
 *   2
 */

fun main3() {
    println("main: ${Thread.currentThread().name}")
    val i = 1
    val plusOne: EagerEffect<String, Int> = eagerEffect {
//        withContext(dispatcher) {
//
//        }
        println("effect: ${Thread.currentThread().name}")
        if (i <= 0)
            shift<Int>("$i is not a positive value")
        println("2")
        i + 1
    }
    plusOne.orNull()
    println("main: ${Thread.currentThread().name}")
}

fun main4(): Unit = runBlocking {
    println("main: ${Thread.currentThread().name}")
    val i = 1
    val plusOne: Effect<String, Int> = effect {
        println("effect: ${Thread.currentThread().name}")
        if (i <= 0)
            shift<Int>("$i is not a positive value")
        println("2")
        i + 1
    }
    plusOne.orNull()
    println("main: ${Thread.currentThread().name}")
}

private val dispatcher = Dispatchers.IO.limitedParallelism(2)
fun main2(): Unit = runBlocking {
    println("main: ${Thread.currentThread().hashCode()}")
    val i = 1
    val plusOne: EagerEffect<String, Int> = eagerEffect {
        println("effect: ${Thread.currentThread().hashCode()}")
        if (i <= 0)
            shift<Int>("$i is not a positive value")
        println("2")
        i + 1
    }
    withContext(dispatcher) {
        joinAll(
            launch {
                println("launch 1: ${Thread.currentThread().hashCode()}")
                plusOne.orNull()
            },
            launch {
                println("launch 2: ${Thread.currentThread().hashCode()}")
                plusOne.orNull()
            },
            launch {
                println("launch 3: ${Thread.currentThread().hashCode()}")
                plusOne.orNull()
            },
            launch {
                println("launch 4: ${Thread.currentThread().hashCode()}")
                plusOne.orNull()
            }
        )
    }
}