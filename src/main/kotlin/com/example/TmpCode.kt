package com.example

import arrow.core.Either
import arrow.core.Option
import arrow.core.continuations.EagerEffect
import arrow.core.continuations.eagerEffect
import arrow.core.continuations.either
import arrow.core.continuations.nullable
import arrow.core.none
import kotlinx.coroutines.runBlocking

fun main() {
//    Either.Right("")
//    Either.Left("")
    val str: Option<String> = Option("")
    println(str.isEmpty())
    val nullableVal: Option<String?> = Option(null)
    println(nullableVal.isEmpty())

    val empty: Option<String> = none()
    println(empty.isEmpty())

//    val e1 = effect<Exception, String> {
//
//    }
//    runBlocking {
//        e1.fold({}, {})
//    }
    val e2: EagerEffect<Exception, String> = eagerEffect {
        println("1")
        ensure(1 != 1) { Exception("not equals") }
        println("2")
        ""
    }
    println("3")
    e2.fold({}, {})

    runBlocking {
        either<Exception, Unit> {

        }
    }
    either.eager<Exception, Unit> {

    }

    val either: Either<Throwable, String> = test()
    either.map {
        1
    }.map {

    }

//    option.eager {
//
//    }
//    ior.eager {
//
//    }

    runBlocking {
        nullable {

        }
    }
}

fun test(): Either<Throwable, String> {
    TODO("Not yet implemented")
}