package com.example.tmp

import arrow.core.Validated
import java.math.BigDecimal
import kotlin.reflect.KProperty0

fun main() {
    val request = FooRequest(
        name = "",
        amount = 2.5.toBigDecimal(),
        address = ""
    )
    request::name.verifyNullOrBlank()
    request::name.test()
}

private fun KProperty0<String?>.test(): Validated<String, Unit> {
    val prop = this
    prop.get()
    TODO("Not yet implemented")
}

private fun KProperty0<String?>.verifyNullOrBlank() {
    val prop = this
    prop.get() ?:
    TODO("Not yet implemented")
}

data class FooRequest(
    val name: String?,
    val amount: BigDecimal?,
    val address: String?
)


////sampleStart
//val throwsSomeStuff: (Int) -> Double = {x -> x.toDouble()}
//val throwsOtherThings: (Double) -> String = {x -> x.toString()}
//val moreThrowing: (String) -> List<String> = {x -> listOf(x)}
//val magic = throwsSomeStuff.andThen(throwsOtherThings).andThen(moreThrowing)
////sampleEnd
//fun main() {
//    println ("magic = $magic")
//    println ("magic = ${magic(1)}")
//    println ("magic = ${magic(0)}")
//
//}