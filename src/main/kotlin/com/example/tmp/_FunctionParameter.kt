package com.example.tmp

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.continuations.ensureNotNull
import java.math.BigDecimal

fun main() {
}

fun doSomething(str: String?, number: BigDecimal?, address: String?) {
    str.validateNullOrBlank("str").map {  }
    val errors = sequence {
        yield(str.validateNullOrBlank("str"))
        yield(address.validateNullOrBlank("address"))
    }.mapNotNull { (it as? Either.Left)?.value }.toList()
}

fun doSomething2(str: String?, number: BigDecimal?, address: String?) {
    str.validateNullOrBlank("str").swap().orNull()
    str.validateNullOrBlank("str").map {  }
    val errors = sequence {
        yield(str.validateNullOrBlank("str"))
        yield(address.validateNullOrBlank("address"))
    }.mapNotNull { (it as? Either.Left)?.value }.toList()
}

fun String?.validateNullOrBlank(fieldName: String): Either<String, Unit> = either.eager {
    val str: String = ensureNotNull(this@validateNullOrBlank) { "'$fieldName' can not be empty." }
    ensure(str.isNotBlank()) { "'$fieldName' can not be empty." }
}

//fun BigDecimal?.validateNullOrPositive(fieldName: String): Either<String, Unit> = either.eager {
//    ensureNotNull(this@validateNullOrPositive) {  }
//}


