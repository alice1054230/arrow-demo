package com.example.smell4

import com.example.exception.CodeEnum
import com.example.exception.MyException
import java.math.BigDecimal
import kotlin.reflect.KProperty0

data class FooRequest(
    val name: String?,
    val amount: BigDecimal?,
    val address: String?,
    val age: BigDecimal?
)

class FooRequestService {
    fun executeFoo(request: FooRequest) {
        val missingParameters = listOfNotNull(
            request::name.findNullOrBlankFieldName(),
            request::amount.findNullFieldName(),
            request::address.findNullOrBlankFieldName(),
            request::age.findNullFieldName()
        )

        if (missingParameters.isNotEmpty())
            throw MyException(CodeEnum.INVALID_PARAM, "missing parameters: ${missingParameters.joinToString(",")}")

        request::amount.findNonPositiveNumber()?.let { fieldName ->
            throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is 0 or negative")
        }

        request::age.findNegativeNumber()?.let { fieldName ->
            throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is negative")
        }
    }
}


private fun KProperty0<Any?>.findNullFieldName(): String? = name.takeIf { this@findNullFieldName.get() == null }

private fun KProperty0<String?>.findNullOrBlankFieldName(): String? =
    name.takeIf { this@findNullOrBlankFieldName.get().isNullOrBlank() }

private fun KProperty0<BigDecimal?>.findNegativeNumber(): String? = name.takeIf {
    this@findNegativeNumber.get()?.let { it < BigDecimal.ZERO } ?: true
}

private fun KProperty0<BigDecimal?>.findNonPositiveNumber(): String? = name.takeIf {
    this@findNonPositiveNumber.get()?.let { it <= BigDecimal.ZERO } ?: true
}